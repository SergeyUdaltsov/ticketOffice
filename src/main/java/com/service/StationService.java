package com.service;

import com.entity.Station;

import java.util.List;

/**
 * Created by Serg on 22.09.2018.
 */
public interface StationService {

    public abstract void addNewStation(Station station);
    public abstract boolean checkIfStationExists(String stationName);
    public abstract List<Station> getAllStations();

}
