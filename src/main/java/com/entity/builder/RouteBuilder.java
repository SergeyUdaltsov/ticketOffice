package com.entity.builder;

import com.entity.Route;
import java.time.LocalDate;
import java.time.LocalTime;

public class RouteBuilder {

    private Route rout;

    public RouteBuilder() {
        this.rout = new Route();
    }

    public RouteBuilder buildDepartureStationRu(String departureStatinRu){
        this.rout.setDepartureStationRu(departureStatinRu);
        return this;
    }

    public RouteBuilder buildArrivalStationRu(String arrivalStationRu) {
        this.rout.setArrivalStationRu(arrivalStationRu);
        return this;
    }

    public RouteBuilder buildTrain(String train) {
        this.rout.setTrain(train);
        return this;
    }

    public RouteBuilder buildDepartureDate(LocalDate date) {
        this.rout.setDepartureDate(date);
        return this;
    }

    public RouteBuilder buildArrivalDate(LocalDate date) {
        this.rout.setArrivalDate(date);
        return this;
    }

    public RouteBuilder buildCode(String routeCode) {
        this.rout.setCode(routeCode);
        return this;
    }

    public RouteBuilder buildId(int routId) {
        this.rout.setId(routId);
        return this;
    }

    public RouteBuilder buildStartStation(int startStationId) {
        this.rout.setStartStationId(startStationId);
        return this;
    }

    public RouteBuilder buildDepartureTime(LocalTime departureTime) {
        this.rout.setDepartureTime(departureTime);
        return this;
    }

    public RouteBuilder buildArrivalTime(LocalTime arrivalTime) {
        this.rout.setArrivalTime(arrivalTime);
        return this;
    }

    public RouteBuilder buildFinishStation(int finishStationId) {
        this.rout.setFinishStationId(finishStationId);
        return this;
    }

    public RouteBuilder buildDepStationString(String stationName) {
        this.rout.setDepartureStation(stationName);
        return this;
    }

    public RouteBuilder buildArrStationString(String stationName) {
        this.rout.setArrivalStation(stationName);
        return this;
    }

    public RouteBuilder buildArrDateString(String arrDate) {
        this.rout.setArrDateString(arrDate);
        return this;
    }

    public RouteBuilder buildArrTimeString(String arrTime) {
        this.rout.setArrTimeString(arrTime);
        return this;
    }

    public RouteBuilder buildDepDateString(String depDate) {
        this.rout.setDepDateString(depDate);
        return this;
    }

    public RouteBuilder buildDepTimeString(String depTime) {
        this.rout.setDepTimeString(depTime);
        return this;
    }

    public Route build() {
        return this.rout;
    }
}
