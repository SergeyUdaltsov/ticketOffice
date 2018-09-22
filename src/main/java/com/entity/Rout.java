package com.entity;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Rout {

    private int id;
    private Station startStation;
    private Station finishStation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private Map<Station, List<LocalTime>> intermediateStations;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getFinishStation() {
        return finishStation;
    }

    public void setFinishStation(Station finishStation) {
        this.finishStation = finishStation;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Map<Station, List<LocalTime>> getIntermediateStations() {
        return intermediateStations;
    }

    public void setIntermediateStations(Map<Station, List<LocalTime>> intermediateStations) {
        this.intermediateStations = intermediateStations;
    }
}
