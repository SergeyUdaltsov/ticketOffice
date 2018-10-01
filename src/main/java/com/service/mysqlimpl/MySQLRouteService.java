package com.service.mysqlimpl;

import com.dao.RouteDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Route;
import com.entity.Station;
import com.entity.builder.RouteBuilder;
import com.entity.builder.StationBuilder;
import com.service.RouteService;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * This is the MySQL implementation of {@code RouteService interface}
 */
public class MySQLRouteService extends MySQLAbstractService implements RouteService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLRouteService.class);

    private final RouteDAO routeDAO;
    private final StationService STATION_SERVICE;

    public MySQLRouteService(RouteDAO routeDAO, StationService stationService) {
        this.routeDAO = routeDAO;
        this.STATION_SERVICE = stationService;
    }

    @Override
    public void addNewRoute(Route route) throws SQLException {

        int routeId = routeDAO.addNewRoute(route);

        Station interStation = STATION_SERVICE.buildIntermediateStation(routeId, route.getStartStationId(),
                route.getDepartureTime(), route.getDepartureTime(), route.getDepartureDate(), true);

        addIntermediateStation(interStation);

        interStation = STATION_SERVICE.buildIntermediateStation(routeId, route.getFinishStationId(),
                route.getArrivalTime(), route.getArrivalTime(), route.getArrivalDate(), true);

        addIntermediateStation(interStation);

        setTrain(DEFAULT_TRAIN_ID, routeId);

    }


    @Override
    public List<Route> getAllRoutes() {

        List<Route> routes = null;

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = routeDAO.getAllRoutes(connection);

            routes = getRoutesFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_ROUTES);

        }
        return routes;

    }

    List<Route> getRoutesFromResultSet(ResultSet resultSet) throws SQLException {

        List<Route> routes = new ArrayList<>();

        while (resultSet.next()) {

            Route route = new RouteBuilder()
                    .buildId(resultSet.getInt("route_id"))
                    .buildCode(resultSet.getString("code"))
                    .buildDepStationString(resultSet.getString("dep_st"))
                    .buildDepartureDate(LocalDate.parse(resultSet.getString("departure_date")))
                    .buildArrivalDate(LocalDate.parse(resultSet.getString("arrival_date")))
                    .buildDepartureTime(LocalTime.parse(resultSet.getString("departure_time")))
                    .buildArrStationString(resultSet.getString("arr_st"))
                    .buildArrivalTime(LocalTime.parse(resultSet.getString("arrival_time")))
                    .build();

            routes.add(route);
        }

        return routes;
    }

    @Override
    public void addIntermediateStation(Station interStation) throws SQLException {

        if (!validateIntermediateStation(interStation) && !interStation.isEndStation()) {
            throw new SQLException(WRONG_DATE_OR_TIME_INTER_STATION);
        }

        routeDAO.addIntermediateStation(interStation);
    }

    private boolean validateIntermediateStation(Station interStation) {

        Route route = getRouteById(interStation.getRouteId());
        LocalDateTime departure = LocalDateTime.of(route.getDepartureDate(), route.getDepartureTime());
        LocalDateTime arrival = LocalDateTime.of(route.getArrivalDate(), route.getArrivalTime());

        LocalDateTime stationDateTime = LocalDateTime.of(interStation.getArrivalDate(), interStation.getArrivalTime());

        return departure.isBefore(stationDateTime)
                && stationDateTime.isBefore(arrival)
                && STATION_SERVICE.validateIntermediateStationTime(interStation);
    }


    @Override
    public List<Station> getIntermediateStationsByRouteId(int routeId) {

        List<Station> intermediateStations = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = routeDAO.getIntermediateStationsByRouteId(connection, routeId);

            intermediateStations = getIntermediateStationsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_RECEIVE_INTERMEDIATE_STATIONS);

        }
        return intermediateStations;
    }


    private List<Station> getIntermediateStationsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Station> intermediateStations = new ArrayList<>();

        while (resultSet.next()) {

            Station station = new StationBuilder()
                    .buildIntermediateId(resultSet.getInt("intermediate_id"))
                    .buildName(resultSet.getString("name"))
                    .buildArrDateTimeString(resultSet.getString("arrival_date_time"))
                    .buildDepTimeString(resultSet.getString("departure_time"))
                    .buildStopping(resultSet.getInt("stopping"))
                    .build();

            intermediateStations.add(station);
        }

        return intermediateStations;
    }


    @Override
    public void deleteRouteById(int routeId) throws SQLException {

        routeDAO.deleteRouteById(routeId);
    }

    @Override
    public void setTrain(int trainId, int routeId) {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SET_TRAIN_TO_ROUTE);

            statement.setInt(1, trainId);

            statement.setInt(2, routeId);

            statement.executeUpdate();

            statement = connection.prepareStatement(SQL_SET_SEATS_TO_INTERMEDIATE);

            List<Integer> seatsCount = getSeatsCountByTrainId(trainId);

            statement.setInt(1, seatsCount.get(0));
            statement.setInt(2, seatsCount.get(1));
            statement.setInt(3, seatsCount.get(2));
            statement.setInt(4, routeId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
    }

    private List<Integer> getSeatsCountByTrainId(int trainId) {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<Integer> seats = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_SEATS_COUNT_BY_TRAIN_ID);

            statement.setInt(1, trainId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                seats.add(resultSet.getInt("economy"));
                seats.add(resultSet.getInt("business"));
                seats.add(resultSet.getInt("comfort"));

            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
        return seats;
    }

    @Override
    public Route getRouteById(int routeId) {

        Route route = null;

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = routeDAO.getRouteById(routeId, connection);

            route = getRouteFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

        }
        return route;
    }

    private Route getRouteFromResultSet(ResultSet resultSet) throws SQLException {

        Route route = null;

        while (resultSet.next()) {

            route = new RouteBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildCode(resultSet.getString("code"))
                    .buildDepStationString(resultSet.getString("dep_st"))
                    .buildDepTimeString(resultSet.getString("dep_time"))
                    .buildDepDateString(resultSet.getString("dep_date"))
                    .buildArrStationString(resultSet.getString("arr_st"))
                    .buildArrTimeString(resultSet.getString("arr_time"))
                    .buildArrDateString(resultSet.getString("arr_date"))
                    .buildDepartureDate(LocalDate.parse(resultSet.getString("dep_date")))
                    .buildArrivalDate(LocalDate.parse(resultSet.getString("arr_date")))
                    .buildArrivalTime(LocalTime.parse(resultSet.getString("arr_time")))
                    .buildDepartureTime(LocalTime.parse(resultSet.getString("dep_time")))
                    .build();
        }
        return route;
    }
}
