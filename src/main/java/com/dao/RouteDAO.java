package com.dao;

import com.entity.Route;
import com.entity.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code RouteDao} interface is responsible for
 * connecting {@code Route} entity class to DB
 */
public interface RouteDAO {

    /**
     * Responsible for saving Route to DB
     *
     * @param route the instance of {@code Route} entity class
     */
    int addNewRoute(Route route) throws SQLException;

    /**
     * Responsible for saving intermediate station to DB
     *
     * @param station the instance of {@code Station} entity class
     */
    void addIntermediateStation(Station station) throws SQLException;

    /**
     * Responsible for deleting Route from DB
     *
     * @param routeId the {@code int} parameter, specifies route.
     */
    void deleteRouteById(int routeId)throws SQLException;

    /**
     * Responsible for getting the list of all Route from DB
     *
     * @param connection java.sql.Connection
     * */
    ResultSet getAllRoutes(Connection connection) throws SQLException;

    /**
     * Responsible for getting the list of all intermediate stations according to
     * specified route from DB.
     *
     * @param connection java.sql.Connection
     * @param routeId the {@code int} parameter, specifies route.
     * */
    ResultSet getIntermediateStationsByRouteId(Connection connection, int routeId) throws SQLException;

    /**
     * Responsible for getting the Route with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param routeId the {@code int} parameter, specifies route.
     * */
    ResultSet getRouteById(int routeId, Connection connection) throws SQLException;

    /**
     * Responsible for setting the Train with specified id to the Route with specified id from DB.
     *
     * @param trainId the {@code int} parameter, specifies train.
     * @param routeId the {@code int} parameter, specifies route.
     * */
    void setTrainToRoute(int routeId, int trainId) throws SQLException;

    /**
     * Responsible for getting the ResultSet from DB containing the data of date and time of Tour.
     *
     * @param routeId the {@code int} parameter, specifies route.
     * @param statement the {@code PreparedStatement} from {@code MySQLRouteService}.
     * */
    ResultSet getDateTimeOfTrip(PreparedStatement statement, int routeId,
                                int stationFrom, int stationTo) throws SQLException;
}
