package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.AbstractEntity;
import com.entity.Route;
import com.entity.Station;
import com.entity.builder.AbstractBuilder;
import com.entity.builder.RouteBuilder;
import com.entity.builder.StationBuilder;
import com.service.RouteService;
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

    @Override
    public void addNewRoute(Route route) throws SQLException {

        AbstractEntity entity = new AbstractBuilder()
                .buildStartStationId(route.getStartStationId())
                .buildDepTime(route.getDepartureTime())
                .buildFinishStationId(route.getFinishStationId())
                .buildArrTime(route.getArrivalTime())
                .buildClass(route.getClass().getSimpleName())
                .buildCode(route.getCode())
                .buildArrDate(route.getArrivalDate())
                .buildDepDate(route.getDepartureDate())
                .build();

        addNewItem(entity, SQL_ADD_NEW_ROUTE);

        int routeId = getRouteIdByCode(entity.getCode());

        entity = populateAbstractEntityStation(routeId, route.getArrivalTime(), route.getDepartureTime(),
                route.getStartStationId(), route.getDepartureDate());

        addIntermediateStation(entity);

        entity = populateAbstractEntityStation(routeId, route.getArrivalTime(), route.getDepartureTime(),
                route.getFinishStationId(), route.getArrivalDate());

        addIntermediateStation(entity);
    }

    private AbstractEntity populateAbstractEntityStation(int routeId, LocalTime arrival, LocalTime departure,
                                                         int interStationId, LocalDate arrDate) {

        AbstractEntity interStation = new AbstractBuilder()
                .buildRouteId(routeId)
                .buildArrTime(arrival)
                .buildDepTime(departure)
                .buildStartStationId(interStationId)
                .buildClass(INTER_STATION)
                .buildArrDate(arrDate)
                .buildEndStation(true)
                .build();

        return interStation;
    }

    @Override
    public List<Route> getAllRoutes() {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<Route> routes = new ArrayList<>();

        try (Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_ROUTES)) {

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

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
        return routes;
    }

    @Override
    public void addIntermediateStation(AbstractEntity interStation) throws SQLException {

        if (!validateIntermediateStation(interStation) && !interStation.isEndStation()) {
            throw new SQLException(WRONG_DATE_OR_TIME_INTER_STATION);
        }

        addNewItem(interStation, SQL_ADD_INTERMEDIATE_STATION);
    }

    Boolean validateIntermediateStation(AbstractEntity interStation) {

        Route route = getRouteById(interStation.getRouteId());

        LocalDateTime departure = LocalDateTime.of(route.getDepartureDate(), route.getDepartureTime());
        LocalDateTime arrival = LocalDateTime.of(route.getArrivalDate(), route.getArrivalTime());

        LocalDateTime stationDateTime = LocalDateTime.of(interStation.getArrivalDate(), interStation.getArrivalTime());

        return (departure.isBefore(stationDateTime) && stationDateTime.isBefore(arrival));
    }

    @Override
    public void deleteIntermediateStationById(int stationId) throws SQLException {

        deleteItem(stationId, SQL_DELETE_INTERMEDIATE_STATION_BY_ID);
    }

    @Override
    public List<AbstractEntity> getIntermediateStationsByRouteId(int routeId) {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<AbstractEntity> intermediateStations = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_INTERMEDIATE_STATIONS_BY_ROUTE);

            statement.setInt(1, routeId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AbstractEntity station = new AbstractBuilder()
                        .buildId(resultSet.getInt("intermediate_id"))
                        .buildStationName(resultSet.getString("name"))
                        .buildArrTime(LocalTime.parse(resultSet.getString("arrival_time")))
                        .buildDepTime(LocalTime.parse(resultSet.getString("departure_time")))
                        .build();

                intermediateStations.add(station);
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
        return intermediateStations;
    }

    @Override
    public void deleteRouteById(int routeId) throws SQLException {

        deleteItem(routeId, SQL_DELETE_ROUTE_BY_ID);
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
    public int getRouteIdByCode(String code) {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        int routeId = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ROUTE_ID_BY_CODE);

            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                routeId = resultSet.getInt(1);

            }


            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return routeId;
    }

    @Override
    public Route getRouteById(int routeId) {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        Route route = null;

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_ROUTE_BY_ID);

            statement.setInt(1, routeId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                route = new RouteBuilder()
                        .buildId(resultSet.getInt(1))
                        .buildCode(resultSet.getString("code"))
                        .buildDepStationString(resultSet.getString("dep_st"))
                        .buildDepartureTime(LocalTime.parse(resultSet.getString("dep_time")))
                        .buildDepartureDate(LocalDate.parse(resultSet.getString("dep_date")))
                        .buildArrStationString(resultSet.getString("arr_st"))
                        .buildArrivalTime(LocalTime.parse(resultSet.getString("arr_time")))
                        .buildArrivalDate(LocalDate.parse(resultSet.getString("arr_date")))
                        .build();
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return route;
    }
}
