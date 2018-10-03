package com.service;

import com.entity.Route;
import com.entity.Station;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code RouteService} interface is responsible for processing business logic
 * with {@code Route} entity class
 */
public interface RouteService {

    /**
     * Responsible for creating new Route instance and save it to DB.
     *
     * @param route is {@code Route} instance to save.
     */
    void addNewRoute(Route route) throws SQLException;

    /**
     * Responsible for getting the list of all Routes from DB.
     *
     * @return {@code List<Route>} the list of all Route from DB.
     * */
    List<Route> getAllRoutes();

    /**
     * Responsible for saving intermediate station to the route.
     *
     * @param interStation the {@code Station} instance to save.
     * */
    void addIntermediateStation(Station interStation) throws SQLException;

    /**
     * Responsible for getting the list of all intermediate stations by specified Route.
     *
     * @param routeId the {@code int} parameter specifies the Route.
     * @return {@code List<Station>} list of intermediate stations by specified route.
     * */
    List<Station> getIntermediateStationsByRouteId(int routeId);

    /**
     * Responsible for deleting specified Route from DB.
     *
     * @param routeId the {@code int} parameter specifies Route.
     * */
    void deleteRouteById(int routeId) throws SQLException;

    /**
     * Responsible for setting the train to the specified Route.
     *
     * @param trainId the {@code int} parameter specifies Train.
     * @param routeId the {@code int} parameter specifies Route.
     * */
    void setTrain(int trainId, int routeId);

    /**
     * Responsible for getting Route instance with specified id.
     *
     * @param routeId the {@code int} parameter specifies Route.
     * @return {@code Route} instance.
     * */
    Route getRouteById(int routeId);


}
