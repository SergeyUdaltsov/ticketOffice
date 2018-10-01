package com.service;

import com.entity.Station;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Serg on 22.09.2018.
 */
public interface StationService {

    void addNewStation(Station station) throws SQLException;

    List<Station> getAllStations();

    Station getStationById(int stationId);

    boolean validateIntermediateStationTime(Station station);

    void updateStation(Station station) throws SQLException;

    void deleteStationById(int stationId, boolean isIntermediate) throws SQLException;

    List<Station> getIntermediateStationsByTrip(int routeId, int depStId, int arrStId) throws SQLException;

    List<String> getDateTimeOfTrip(int routeId, int stationFrom, int stationTo) throws SQLException;

    Station buildIntermediateStation(int routeId, int stationId, LocalTime arrTime,
                                     LocalTime depTime, LocalDate arrDate, boolean endStation);


}
