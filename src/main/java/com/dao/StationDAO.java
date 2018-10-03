package com.dao;

import com.entity.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The {@code StationDao} interface is responsible for
 * connecting {@code Station} entity class to DB
 */
public interface StationDAO {

    /**
     * Responsible for saving new Station to DB
     *
     * @param station the instance of {@code Station} entity class
     */
    void addNewStation(Station station) throws SQLException;

    /**
     * Responsible for getting the ResultSet from DB containing the data of arrival and departure due to the station.
     *
     * @param interStation the {@code Station} instance parameter, specifies Station.
     * @param connection java.sql.Connection.
     * */
    ResultSet getResultSetStationTimes(Station interStation, Connection connection) throws SQLException;

    /**
     * Responsible for deleting Station from DB
     *
     * @param stationId the {@code int} parameter, specifies station.
     * @param intermediate the {@code boolean} parameter specifies if the station is intermediate.
     */
    void deleteStationById(int stationId, boolean intermediate) throws SQLException;

    /**
     * Responsible for setting count of available seats by class to the Station according to Route.
     *
     * @param seats the {@code List<Integer>} parameter containing data of count of seats.
     * @param routeId the{@code int} parameter specifies the corresponding Route.
     * */
    void setSeatsToIntermediateStations(List<Integer> seats, int routeId) throws SQLException;

    /**
     * Responsible for getting the list of all Station from DB
     *
     * @param connection java.sql.Connection
     * */
    ResultSet getAllStations(Connection connection) throws SQLException;

    /**
     * Responsible for getting the list of all intermediate stations according to
     * specified parameters of tour from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param dateTimes the {@code List<String>} parameter containing data of departure and arrival of tour.
     * @param routeId the{@code int} parameter specifies the corresponding Route.
     * */
    ResultSet getIntermediateStationsByTrip(PreparedStatement statement, List<String> dateTimes,
                                            int routeId) throws SQLException;


    /**
     * Responsible for getting the specified Station from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param stationId the{@code int} parameter specifies the corresponding Station.
     * */
    ResultSet getStationById(PreparedStatement statement, int stationId) throws SQLException;

    /**
     * Responsible for updating Station in DB
     *
     * @param station the instance of {@code Station} entity class
     */
    void updateStation(Station station) throws SQLException;


}
