package com.entity;

import java.time.LocalTime;

public class TicketOrder {

    private int id;
    private User user;
    private int countOfEconomy;
    private int countOfBusiness;
    private int countOfComfort;
    private Station stationFrom;
    private Station stationTo;
    private LocalTime departureTime;
    private int routeId;
    private double ecoPrice;
    private double busPrice;
    private double comPrice;




    public double getEcoPrice() {
        return ecoPrice;
    }

    public void setEcoPrice(double ecoPrice) {
        this.ecoPrice = ecoPrice;
    }

    public double getBusPrice() {
        return busPrice;
    }

    public void setBusPrice(double busPrice) {
        this.busPrice = busPrice;
    }

    public double getComPrice() {
        return comPrice;
    }

    public void setComPrice(double comPrice) {
        this.comPrice = comPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public int getCountOfEconomy() {
        return countOfEconomy;
    }

    public void setCountOfEconomy(int countOfEconomy) {
        this.countOfEconomy = countOfEconomy;
    }

    public int getCountOfBusiness() {
        return countOfBusiness;
    }

    public void setCountOfBusiness(int countOfBusiness) {
        this.countOfBusiness = countOfBusiness;
    }

    public int getCountOfComfort() {
        return countOfComfort;
    }

    public void setCountOfComfort(int countOfComfort) {
        this.countOfComfort = countOfComfort;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(Station stationFrom) {
        this.stationFrom = stationFrom;
    }

    public Station getStationTo() {
        return stationTo;
    }

    public void setStationTo(Station stationTo) {
        this.stationTo = stationTo;
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
}
