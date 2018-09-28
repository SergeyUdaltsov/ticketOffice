package com.service;

import com.entity.AbstractEntity;
import com.entity.Route;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 24.09.2018.
 */
public interface RouteService {

    void addNewRoute(Route route) throws SQLException;

    List<Route> getAllRoutes();

    void addIntermediateStation(AbstractEntity interStation) throws SQLException;

    void deleteIntermediateStationById(int stationId) throws SQLException;

    List<AbstractEntity> getIntermediateStationsByRouteId(int routeId);

    void deleteRouteById(int routeId) throws SQLException;

    void setTrain(int trainId, int routeId);

    int getRouteIdByCode(String code);

    Route getRouteById(int routId);



}
