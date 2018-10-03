package com.dao.impl;

import com.dao.CommonsOperable;
import com.dao.RouteDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Route;
import com.entity.Station;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.utils.UtilConstants.*;

/**
 * The {@code JDBCRouteDao} class is a JDBC implementation
 * of {@code RouteDao} interface
 */
public class JDBCRouteDAO implements RouteDAO, CommonsOperable {

    private static final Logger LOGGER = LogManager.getLogger(JDBCRouteDAO.class);

    /**
     * Receives route and saves it into DB
     *
     * @param route the instance of {@code Route} entity class
     */
    @Override
    public int addNewRoute(Route route) throws SQLException {

        int routeId = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_ADD_NEW_ROUTE, Statement.RETURN_GENERATED_KEYS)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, route.getCode());
            statement.setInt(2, route.getStartStationId());
            statement.setString(3, route.getDepartureTime().toString());
            statement.setString(4, route.getDepartureDate().toString());
            statement.setString(5, route.getArrivalDate().toString());
            statement.setInt(6, route.getFinishStationId());
            statement.setString(7, route.getArrivalTime().toString());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
               routeId = rs.getInt(1);
            }

            LOGGER.info(ROUTE + CREATED + FROM + route.getStartStationId() + TO + route.getFinishStationId());

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_ROUTE);
        }

        return routeId;
    }

    /**
     * Receives intermediate station and saves it into DB
     *
     * @param station the instance of {@code Station} entity class
     */
    @Override
    public void addIntermediateStation(Station station) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_INTERMEDIATE_STATION)) {

            MySQLConnectorManager.startTransaction(connection);

            LocalDateTime arrDateTime = LocalDateTime.of(station.getArrivalDate(), station.getArrivalTime());
            LocalDateTime depDateTime = station.getDepartureTime().atDate(station.getArrivalDate());

            long stopping = arrDateTime.until(depDateTime, ChronoUnit.MINUTES);

            statement.setInt(1, station.getRouteId());
            statement.setInt(2, station.getId());
            statement.setLong(4, stopping);
            statement.setString(5, station.getDepartureTime().toString());
            statement.setString(3, arrDateTime.toString());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

            LOGGER.info(INTERMEDIATE_STATION_ADDED);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_STATION);
        }

    }

    /**
     * Receives the id of route and deletes it from DB.
     *
     * @param routeId the {@code int} parameter specifies the Route.
     * */
    @Override
    public void deleteRouteById(int routeId) throws SQLException {

        deleteItemById(routeId, SQL_DELETE_ROUTE_BY_ID);

        LOGGER.info(ROUTE + routeId + DELETED);
    }

    /**
     * Receives the connection and returns all the routes from DB.
     *
     * @return ResultSet containing al the data of all the Routes from DB.
     * */
    @Override
    public ResultSet getAllRoutes(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_ROUTES);
    }

    /**
     * Receives the connection and returns the ResultSet with data of all the
     * intermediate stations by the route from DB.
     *
     * @return ResultSet containing al the data of all the Stations by the route from DB.
     * @param routeId the {@code int} parameter specifies the Route.
     * */
    @Override
    public ResultSet getIntermediateStationsByRouteId(Connection connection, int routeId) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(SQL_GET_INTERMEDIATE_STATIONS_BY_ROUTE);

        statement.setInt(1, routeId);

        return statement.executeQuery();
    }

    /**
     * Responsible for getting the Route with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param routeId the {@code int} parameter, specifies route.
     * @return ResultSet with all the data of Route.
     * */
    @Override
    public ResultSet getRouteById(int routeId, Connection connection) throws SQLException {

        return getItemById(connection, SQL_GET_ROUTE_BY_ID, routeId);

    }

    /**
     * Responsible for setting the Train with specified id to the Route with specified id from DB.
     *
     * @param trainId the {@code int} parameter, specifies train.
     * @param routeId the {@code int} parameter, specifies route.
     * */
    @Override
    public void setTrainToRoute(int routeId, int trainId) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_TRAIN_TO_ROUTE)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, trainId);

            statement.setInt(2, routeId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_SET_TRAIN_TO_ROUTE);
        }
    }

    /**
     * Responsible for getting the ResultSet from DB containing the data of date and time of Tour.
     *
     * @param routeId the {@code int} parameter, specifies route.
     * @param statement the {@code PreparedStatement} from {@code MySQLRouteService}.
     * @param stationFrom the {@code int} parameter, specifies departure Station.
     * @param stationTo the {@code int} parameter, specifies arrival Station.
     * */
    @Override
    public ResultSet getDateTimeOfTrip(PreparedStatement statement, int routeId,
                                       int stationFrom, int stationTo) throws SQLException {

        statement.setInt(1, routeId);
        statement.setInt(2, stationFrom);
        statement.setInt(3, routeId);
        statement.setInt(4, stationTo);

        return statement.executeQuery();
    }
}
