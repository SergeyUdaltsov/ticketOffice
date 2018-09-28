package com.entity.builder;

import com.entity.Route;
import com.entity.Station;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class RouteBuilder {

    private Route rout;

    public RouteBuilder() {
        this.rout = new Route();
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

    public RouteBuilder buildIntermediateStation(Map<Station, List<LocalTime>> intermediateStations) {
        this.rout.setIntermediateStations(intermediateStations);
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

    public Route build() {
        return this.rout;
    }
}
