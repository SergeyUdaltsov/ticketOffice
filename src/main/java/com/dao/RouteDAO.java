package com.dao;

import com.entity.Route;
import com.entity.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Serg on 30.09.2018.
 */
public interface RouteDAO {

    int addNewRoute(Route route) throws SQLException;

    void addIntermediateStation(Station station) throws SQLException;

    void deleteRouteById(int routeId)throws SQLException;

    ResultSet getAllRoutes(Connection connection) throws SQLException;

    ResultSet getIntermediateStationsByRouteId(Connection connection, int routeId) throws SQLException;

    ResultSet getRouteById(int routeId, Connection connection) throws SQLException;

    void setTrainToRoute(int routeId, int trainId) throws SQLException;

    ResultSet getDateTimeOfTrip(PreparedStatement statement, int routeId, int stationFrom, int stationTo) throws SQLException;
}
