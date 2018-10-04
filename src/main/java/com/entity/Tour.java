package com.entity;

/**
 * Created by Serg on 01.10.2018.
 */
public class Tour {

    private String code;
    private int routeId;
    private String arrivalTimeDateStart;
    private String departureTime;
    private String departureStation;
    private String departureStationRu;
    private int departureStationId;
    private String arrivalTimeDateFinish;
    private String arrivalStation;
    private String arrivalStationRu;
    private int arrivalStationId;
    private String tourTime;
    private double tourPriceEco;
    private double tourPriceBusiness;
    private double tourPriceComfort;

    public void setDepartureStationRu(String departureStationRu) {
        this.departureStationRu = departureStationRu;
    }

    public void setArrivalStationRu(String arrivalStationRu) {
        this.arrivalStationRu = arrivalStationRu;
    }

    public void setTourTime(String tourTime) {
        this.tourTime = tourTime;
    }

    public void setTourPriceEco(double tourPriceEco) {
        this.tourPriceEco = tourPriceEco;
    }

    public void setTourPriceBusiness(double tourPriceBusiness) {
        this.tourPriceBusiness = tourPriceBusiness;
    }

    public void setTourPriceComfort(double tourPriceComfort) {
        this.tourPriceComfort = tourPriceComfort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getArrivalTimeDateStart() {
        return arrivalTimeDateStart;
    }

    public void setArrivalTimeDateStart(String arrivalTimeDateStart) {
        this.arrivalTimeDateStart = arrivalTimeDateStart;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public void setDepartureStationId(int departureStationId) {
        this.departureStationId = departureStationId;
    }

    public String getArrivalTimeDateFinish() {
        return arrivalTimeDateFinish;
    }

    public void setArrivalTimeDateFinish(String arrivalTimeDateFinish) {
        this.arrivalTimeDateFinish = arrivalTimeDateFinish;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public void setArrivalStationId(int arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }
}

