package com.service;

import com.entity.Route;
import com.entity.Station;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 24.09.2018.
 */
public interface RouteService {

    void addNewRoute(Route route) throws SQLException;

    List<Route> getAllRoutes();

    void addIntermediateStation(Station interStation) throws SQLException;

    List<Station> getIntermediateStationsByRouteId(int routeId);

    void deleteRouteById(int routeId) throws SQLException;

    void setTrain(int trainId, int routeId);

    Route getRouteById(int routId);



}
