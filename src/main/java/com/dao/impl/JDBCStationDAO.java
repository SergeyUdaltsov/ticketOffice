package com.dao.impl;

import com.dao.CommonsOperable;
import com.dao.StationDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Station;
import com.service.mysqlimpl.MySQLUserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 01.10.2018.
 */
public class JDBCStationDAO implements StationDAO, CommonsOperable {

    private static final Logger LOGGER = LogManager.getLogger(JDBCStationDAO.class);


    @Override
    public void addNewStation(Station station) {

    }

    @Override
    public ResultSet getResultSetStationTimes(Station station, Connection connection) throws SQLException {

        ResultSet resultSet = null;

        PreparedStatement statement = connection.prepareStatement(SQL_GET_STATIONS_TIME);

        statement.setInt(1, station.getRouteId());

        resultSet = statement.executeQuery();


        return resultSet;
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

}
