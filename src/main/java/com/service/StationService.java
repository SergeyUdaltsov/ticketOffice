package com.service;

import com.entity.Station;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 22.09.2018.
 */
public interface StationService {

    public abstract void addNewStation(Station station) throws SQLException;
    public abstract List<Station> getAllStations();
    public abstract Station getStationById(int stationId);
    public abstract void updateStation(Station station) throws SQLException;
    public abstract void deleteStationById(int stationId) throws SQLException;

}
