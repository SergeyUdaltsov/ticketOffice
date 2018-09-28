package com.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Route implements Sendable {

    private int id;
    private int startStationId;
    private int finishStationId;
    private String departureStation;
    private String arrivalStation;
    private String code;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String train;

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    private Map<Station, List<LocalTime>> intermediateStations;


    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(int startStationId) {
        this.startStationId = startStationId;
    }

    public int getFinishStationId() {
        return finishStationId;
    }

    public void setFinishStationId(int finishStationId) {
        this.finishStationId = finishStationId;
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
