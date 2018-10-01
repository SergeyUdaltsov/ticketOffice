package com.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Station implements Sendable{

    private int id;
    private int routeId;
    private String name;
    private int intermediateId;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private String arrDateTimeString;
    private String depTimeString;
    private int stopping;
    private boolean endStation;



    public boolean isEndStation() {
        return endStation;
    }

    public void setEndStation(boolean endStation) {
        this.endStation = endStation;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getStopping() {
        return stopping;
    }

    public void setStopping(int stopping) {
        this.stopping = stopping;
    }

    public int getIntermediateId() {
        return intermediateId;
    }

    public void setIntermediateId(int intermediateId) {
        this.intermediateId = intermediateId;
    }

    public String getArrDateTimeString() {
        return arrDateTimeString;
    }

    public void setArrDateTimeString(String arrDateTimeString) {
        this.arrDateTimeString = arrDateTimeString;
    }

    public String getDepTimeString() {
        return depTimeString;
    }

    public void setDepTimeString(String depTimeString) {
        this.depTimeString = depTimeString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
