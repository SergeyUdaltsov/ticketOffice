package com.dao.impl;

import com.dao.CommonsOperable;
import com.dao.StationDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Station;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code JDBCStationDao} class is a JDBC implementation
 * of {@code StationDAO} interface
 */
public class JDBCStationDAO implements StationDAO, CommonsOperable {

    private static final Logger LOGGER = LogManager.getLogger(JDBCStationDAO.class);

    /**
     * Responsible for saving new Station to DB
     *
     * @param station the instance of {@code Station} entity class
     */
    @Override
    public void addNewStation(Station station) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
               PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_STATION)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, station.getName());
            statement.setString(2, station.getNameRu());

            statement.executeUpdate();
            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(STATION_EXISTS);
        }
    }

    /**
     * Responsible for getting the ResultSet from DB containing the data of arrival and departure due to the station.
     *
     * @param station the {@code Station} instance parameter, specifies Station.
     * @param connection java.sql.Connection.
     * */
    @Override
    public ResultSet getResultSetStationTimes(Station station, Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(SQL_GET_STATIONS_TIME);

        statement.setInt(1, station.getRouteId());

        return statement.executeQuery();
    }

    /**
     * Responsible for deleting Station from DB
     *
     * @param stationId the {@code int} parameter, specifies station.
     * @param intermediate the {@code boolean} parameter specifies if the station is intermediate.
     */
    @Override
    public void deleteStationById(int stationId, boolean intermediate) throws SQLException {

        String query = SQL_DELETE_INTERMEDIATE_STATION_BY_ID;

        if (!intermediate) {
            query = SQL_DELETE_STATION;
        }

        deleteItemById(stationId, query);

        LOGGER.info(STATION + stationId + DELETED);
    }

    /**
     * Responsible for setting count of available seats by class to the Station according to Route.
     *
     * @param seats the {@code List<Integer>} parameter containing data of count of seats.
     * @param routeId the{@code int} parameter specifies the corresponding Route.
     * */
    @Override
    public void setSeatsToIntermediateStations(List<Integer> seats, int routeId) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_SEATS_TO_INTERMEDIATE)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, seats.get(0));
            statement.setInt(2, seats.get(1));
            statement.setInt(3, seats.get(2));
            statement.setInt(4, routeId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {
            throw new SQLException(COULD_NOT_SET_SEATS);
        }
    }

    /**
     * Responsible for getting the list of all Station from DB
     *
     * @param connection java.sql.Connection
     * */
    @Override
    public ResultSet getAllStations(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_STATIONS);

    }

    /**
     * Responsible for getting the list of all intermediate stations according to
     * specified parameters of tour from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param dateTimes the {@code List<String>} parameter containing data of departure and arrival of tour.
     * @param routeId the{@code int} parameter specifies the corresponding Route.
     * */
    @Override
    public ResultSet getIntermediateStationsByTrip(PreparedStatement statement, List<String> dateTimes,
                                                   int routeId) throws SQLException {

        statement.setString(1, dateTimes.get(0));
        statement.setString(2, dateTimes.get(1));
        statement.setInt(3, routeId);

        return statement.executeQuery();
    }

    /**
     * Responsible for getting the ResultSet with all the data of specified Station from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param stationId the{@code int} parameter specifies the corresponding Station.
     * */
    @Override
    public ResultSet getStationById(PreparedStatement statement, int stationId) throws SQLException {

        statement.setInt(1, stationId);

        return statement.executeQuery();
    }

    /**
     * Responsible for updating Station in DB
     *
     * @param station the instance of {@code Station} entity class
     */
    @Override
    public void updateStation(Station station) throws SQLException {

        try(Connection connection = MySQLConnectorManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATION)){

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, station.getName());
            statement.setString(2, station.getNameRu());
            statement.setInt(3, station.getId());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }

    }

}
