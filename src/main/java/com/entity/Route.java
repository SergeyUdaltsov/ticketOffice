package com.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Route implements Sendable {

    private int id;
    private int startStationId;
    private int finishStationId;
    private String departureStation;
    private String depTimeString;
    private String depDateString;
    private String arrTimeString;
    private String arrDateString;
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

    public String getDepTimeString() {
        return depTimeString;
    }

    public void setDepTimeString(String depTimeString) {
        this.depTimeString = depTimeString;
    }

    public String getDepDateString() {
        return depDateString;
    }

    public void setDepDateString(String depDateString) {
        this.depDateString = depDateString;
    }

    public String getArrTimeString() {
        return arrTimeString;
    }

    public void setArrTimeString(String arrTimeString) {
        this.arrTimeString = arrTimeString;
    }

    public String getArrDateString() {
        return arrDateString;
    }

    public void setArrDateString(String arrDateString) {
        this.arrDateString = arrDateString;
    }
}
