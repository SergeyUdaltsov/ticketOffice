package com.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Station {

    private int id;
    private int routeId;
    private String name;
    private String nameRu;
    private int intermediateId;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private String arrDateTimeString;
    private String depTimeString;
    private int stopping;
    private boolean endStation;


    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

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

    public void setStopping(int stopping) {
        this.stopping = stopping;
    }

    public void setIntermediateId(int intermediateId) {
        this.intermediateId = intermediateId;
    }

    public void setArrDateTimeString(String arrDateTimeString) {
        this.arrDateTimeString = arrDateTimeString;
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
