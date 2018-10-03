package com.service;

import com.entity.Station;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The {@code StationService} interface is responsible for processing business logic
 * with {@code Station} entity class
 */
public interface StationService {

    /**
     * Responsible for creating new Station instance and save it to DB.
     *
     * @param station is {@code Station} instance to save.
     */
    void addNewStation(Station station) throws SQLException;

    /**
     * Responsible for getting the list of all Stations from DB.
     *
     * @return {@code List<Station>} the list of all Station from DB.
     * */
    List<Station> getAllStations();

    /**
     * Responsible for getting Station instance with specified id.
     *
     * @param stationId the {@code int} parameter specifies Station.
     * @return {@code Station} instance with specified id.
     * */
    Station getStationById(int stationId);

    /**
     * Responsible for validating time of intermediate station.
     * It must be not before than departure time from start station and
     * not after the arrival to the end station of the route.
     *
     * @param station the {@code Station} instance
     * @return {@code boolean} result.
     * */
    boolean validateIntermediateStationTime(Station station);

    /**
     * Responsible for updating station with specified id with new values.
     *
     * @param station the {@code Station} instance encapsulating new values.
     * */
    void updateStation(Station station) throws SQLException;

    /**
     * Responsible for deleting specified Station from DB.
     *
     * @param stationId the {@code int} parameter specifies Station.
     * @param isIntermediate the {@code boolean} parameter specifies if the station is intermediate.
     * */
    void deleteStationById(int stationId, boolean isIntermediate) throws SQLException;

    /**
     * Responsible for getting list of all the intermediate stations between specified stations
     * according to the route.
     *
     * @param routeId the {@code int} parameter specifies the Route.
     * @param depStId the {@code int} parameter specifies the departure Station.
     * @param arrStId the {@code int} parameter specifies the arrival Station.
     * @return {@code List<Station>} the list of all intermediate stations between specified stations.
     * */
    List<Station> getIntermediateStationsByTrip(int routeId, int depStId, int arrStId) throws SQLException;

    /**
     * Responsible for calculating the date and time of start station and date and time of finish station
     * according to route with specified id.
     *
     * @param routeId the {@code int} parameter specifies Route.
     * @param stationFrom the {@code int} parameter specifies departure station.
     * @param stationTo the {@code int} parameter specifies arrival station.
     * */
    List<String> getDateTimeOfTrip(int routeId, int stationFrom, int stationTo) throws SQLException;

    /**
     * Responsible for building Station instance from list of data.
     *
     * @param routeId the {@code int} parameter specifies Route.
     * @param stationId the {@code int} parameter specifies Station id.
     * @param arrTime the {@code LocalTime} instance specifies time of arrival to station.
     * @param depTime the {@code LocalTime} instance specifies time of departure from station.
     * @param arrDate the {@code LocalDate} instance specifies date of arrival to station.
     * */
    Station buildIntermediateStation(int routeId, int stationId, LocalTime arrTime,
                                     LocalTime depTime, LocalDate arrDate, boolean endStation);


}
