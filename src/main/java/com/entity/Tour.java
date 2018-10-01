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
    private int departureStationId;
    private String arrivalTimeDateFinish;
    private String arrivalStation;
    private int arrivalStationId;
    private String tourTime;
    private long tourPrice;

    public String getTourTime() {
        return tourTime;
    }

    public void setTourTime(String tourTime) {
        this.tourTime = tourTime;
    }

    public long getTourPrice() {
        return tourPrice;
    }

    public void setTourPrice(long tourPrice) {
        this.tourPrice = tourPrice;
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

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public int getDepartureStationId() {
        return departureStationId;
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

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public int getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(int arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }
}


//    buildCode(resultSet.getString("code"))
//        .buildRouteId(resultSet.getInt("route_id"))
//        .buildArrTimeDateString(resultSet.getString("arr_date_from"))
//        .buildDepTimeString(resultSet.getString("dep_time"))
//        .buildDepStation(resultSet.getString("dep_st"))
//        .buildStartStationId(resultSet.getInt("dep_st_id"))
//        .buildArrTimeDateFinString(resultSet.getString("arr_date_to"))
//        .buildArrStation(resultSet.getString("arr_st"))
//        .buildFinishStationId(resultSet.getInt("arr_st_id"))
//        .build();