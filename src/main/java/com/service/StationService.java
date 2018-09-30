package com.service;

import com.entity.Station;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 22.09.2018.
 */
public interface StationService {

    void addNewStation(Station station) throws SQLException;

    List<Station> getAllStations();

    Station getStationById(int stationId);

    void updateStation(Station station) throws SQLException;

    void deleteStationById(int stationId) throws SQLException;

    List<Station> getIntermediateStationsByTrip(int routeId, int depStId, int arrStId) throws SQLException;

    List<String> getDateTimeOfTrip(int routeId, int stationFrom, int stationTo) throws SQLException;


}
