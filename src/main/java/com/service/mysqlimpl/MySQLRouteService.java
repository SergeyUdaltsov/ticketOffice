package com.service.mysqlimpl;

import com.dao.RouteDAO;
import com.dao.StationDAO;
import com.dao.TrainDAO;
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
public class MySQLRouteService implements RouteService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLRouteService.class);

    private final RouteDAO routeDAO;//to uppercase
    private final TrainDAO trainDAO;
    private final StationDAO stationDAO;
    private final StationService STATION_SERVICE;

    public MySQLRouteService(RouteDAO routeDAO, TrainDAO trainDAO,
                             StationDAO stationDAO, StationService STATION_SERVICE) {
        this.routeDAO = routeDAO;
        this.trainDAO = trainDAO;
        this.stationDAO = stationDAO;
        this.STATION_SERVICE = STATION_SERVICE;
    }

    /**
     * Responsible for creating new Route instance and save it to DB.
     * Creates two intermediate stations which are the start and finish stations of the route.
     * Sets the default train to the route.
     *
     * @param route is {@code Route} instance to save.
     */
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

    /**
     * Responsible for getting the list of all Routes from DB.
     *
     * @return {@code List<Route>} the list of all Route from DB.
     */
    @Override
    public List<Route> getAllRoutes() {

        List<Route> routes = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = routeDAO.getAllRoutes(connection);

            routes = getRoutesFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_ROUTES);

        }
        return routes;

    }

    /**
     * Responsible for building the list of Route instance from the list of data encapsulated in ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getAllRoutes()} method.
     * @return {@code List<Route>} from ResultSet.
     */
    private List<Route> getRoutesFromResultSet(ResultSet resultSet) throws SQLException {

        List<Route> routes = new ArrayList<>();

        while (resultSet.next()) {

            Route route = new RouteBuilder()
                    .buildId(resultSet.getInt("route_id"))
                    .buildCode(resultSet.getString("code"))
                    .buildDepStationString(resultSet.getString("dep_st"))
                    .buildDepartureStationRu(resultSet.getString("dep_st_ru"))
                    .buildDepartureDate(LocalDate.parse(resultSet.getString("departure_date")))
                    .buildArrivalDate(LocalDate.parse(resultSet.getString("arrival_date")))
                    .buildDepartureTime(LocalTime.parse(resultSet.getString("departure_time")))
                    .buildArrStationString(resultSet.getString("arr_st"))
                    .buildArrivalStationRu(resultSet.getString("arr_st_ru"))
                    .buildArrivalTime(LocalTime.parse(resultSet.getString("arrival_time")))
                    .build();

            routes.add(route);
        }

        return routes;
    }

    /**
     * Responsible for saving intermediate station to the route.
     * Validates station's date, time and name.
     *
     * @param interStation the {@code Station} instance to save.
     */
    @Override
    public void addIntermediateStation(Station interStation) throws SQLException {

        if (!validateIntermediateStation(interStation) && !interStation.isEndStation()) {
            throw new SQLException(WRONG_DATE_OR_TIME_INTER_STATION);
        }

        routeDAO.addIntermediateStation(interStation);
    }

    /**
     * Responsible for validating intermediate station date and time.
     * Checks if the date-time point of arrival to the intermediate station is after the departure
     * from the start station of the route and before the arrival to the finish station of the route.
     * Checks if the station is end station of the route.
     * Checks if the date-time point of station does not intersect date-time points of other intermediate stations.
     *
     * @param interStation the {@code Station} instance encapsulating all the data of intermediate station.
     */
    private boolean validateIntermediateStation(Station interStation) {

        Route route = getRouteById(interStation.getRouteId());
        LocalDateTime departure = LocalDateTime.of(route.getDepartureDate(), route.getDepartureTime());
        LocalDateTime arrival = LocalDateTime.of(route.getArrivalDate(), route.getArrivalTime());

        LocalDateTime stationDateTime = LocalDateTime.of(interStation.getArrivalDate(), interStation.getArrivalTime());

        return departure.isBefore(stationDateTime) &&
                stationDateTime.isBefore(arrival) &&
                STATION_SERVICE.validateIntermediateStationTime(interStation);
    }


    /**
     * Responsible for getting the list of all intermediate stations by specified Route.
     *
     * @param routeId the {@code int} parameter specifies the Route.
     * @return {@code List<Station>} list of intermediate stations by specified route.
     */
    @Override
    public List<Station> getIntermediateStationsByRouteId(int routeId) {

        List<Station> intermediateStations = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = routeDAO.getIntermediateStationsByRouteId(connection, routeId);

            intermediateStations = getIntermediateStationsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_RECEIVE_INTERMEDIATE_STATIONS);

        }
        return intermediateStations;
    }


    /**
     * Responsible for building list of Station from ResultSet.
     *
     * @param resultSet the {@code ResultSet} instance encapsulating data of Station.
     * */
    private List<Station> getIntermediateStationsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Station> intermediateStations = new ArrayList<>();

        while (resultSet.next()) {

            Station station = new StationBuilder()
                    .buildIntermediateId(resultSet.getInt("intermediate_id"))
                    .buildName(resultSet.getString("name"))
                    .buildNameRu(resultSet.getString("name_ru"))
                    .buildArrDateTimeString(resultSet.getString("arrival_date_time"))
                    .buildDepTimeString(resultSet.getString("departure_time"))
                    .buildStopping(resultSet.getInt("stopping"))
                    .build();

            intermediateStations.add(station);
        }

        return intermediateStations;
    }


    /**
     * Responsible for deleting specified Route from DB.
     *
     * @param routeId the {@code int} parameter specifies Route.
     * */
    @Override
    public void deleteRouteById(int routeId) throws SQLException {

        routeDAO.deleteRouteById(routeId);
    }

    /**
     * Responsible for setting the train to the specified Route.
     * Adds values of available tickets to all the stations of the route according to the train.
     *
     * @param trainId the {@code int} parameter specifies Train.
     * @param routeId the {@code int} parameter specifies Route.
     * */
    @Override
    public void setTrain(int trainId, int routeId) {

        try {

            routeDAO.setTrainToRoute(routeId, trainId);

            List<Integer> seatsCount = trainDAO.getSeatsCountByTrainId(trainId);

            stationDAO.setSeatsToIntermediateStations(seatsCount, routeId);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Responsible for getting Route instance with specified id.
     *
     * @param routeId the {@code int} parameter specifies Route.
     * @return {@code Route} instance with specified id.
     * */
    @Override
    public Route getRouteById(int routeId) {

        Route route = new Route();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = routeDAO.getRouteById(routeId, connection);

            route = getRouteFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

        }

        return route;
    }

    /**
     * Responsible for building Route instance from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getRouteById()} method instance encapsulating data of Route.
     * */
    private Route getRouteFromResultSet(ResultSet resultSet) throws SQLException {

        Route route = new Route();

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
