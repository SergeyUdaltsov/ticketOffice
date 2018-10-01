package com.dao;

import com.entity.Station;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Serg on 01.10.2018.
 */
public interface StationDAO {


    void addNewStation(Station station);

    ResultSet getResultSetStationTimes(Station interStation, Connection connection) throws SQLException;

    void deleteStationById(int stationId, boolean intermediate) throws SQLException;
}
