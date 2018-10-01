package com.dao;

import com.entity.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 01.10.2018.
 */
public interface StationDAO {


    void addNewStation(Station station) throws SQLException;

    ResultSet getResultSetStationTimes(Station interStation, Connection connection) throws SQLException;

    void deleteStationById(int stationId, boolean intermediate) throws SQLException;

    void setSeatsToIntermediateStations(List<Integer> seats, int routeId) throws SQLException;

    ResultSet getAllStations(Connection connection) throws SQLException;

    ResultSet getIntermediateStationsByTrip(PreparedStatement statement, List<String> dateTimes, int routeId) throws SQLException;

    ResultSet getStationById(PreparedStatement statement, int stationId) throws SQLException;

    void updateStation(Station station) throws SQLException;


}
