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
 * Created by Serg on 01.10.2018.
 */
public class JDBCStationDAO implements StationDAO, CommonsOperable {

    private static final Logger LOGGER = LogManager.getLogger(JDBCStationDAO.class);

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

    @Override
    public ResultSet getResultSetStationTimes(Station station, Connection connection) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(SQL_GET_STATIONS_TIME);

        statement.setInt(1, station.getRouteId());

        return statement.executeQuery();
    }

    @Override
    public void deleteStationById(int stationId, boolean intermediate) throws SQLException {

        String query = SQL_DELETE_INTERMEDIATE_STATION_BY_ID;

        if (!intermediate) {
            query = SQL_DELETE_STATION;
        }

        deleteItemById(stationId, query);

        LOGGER.info(STATION + stationId + DELETED);
    }

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

    @Override
    public ResultSet getAllStations(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_STATIONS);

    }

    @Override
    public ResultSet getIntermediateStationsByTrip(PreparedStatement statement, List<String> dateTimes,
                                                   int routeId) throws SQLException {

        statement.setString(1, dateTimes.get(0));
        statement.setString(2, dateTimes.get(1));
        statement.setInt(3, routeId);

        return statement.executeQuery();
    }

    @Override
    public ResultSet getStationById(PreparedStatement statement, int stationId) throws SQLException {

        statement.setInt(1, stationId);

        return statement.executeQuery();
    }

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
