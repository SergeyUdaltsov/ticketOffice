package com.entity.builder;

import com.entity.Station;

import java.time.LocalDate;
import java.time.LocalTime;

public class StationBuilder {

    private Station station;

    public StationBuilder() {
        this.station = new Station();
    }



    public StationBuilder buildNameRu(String nameRu){
        this.station.setNameRu(nameRu);
        return this;
    }

    public StationBuilder buildEndStation(boolean isEnd){
        this.station.setEndStation(isEnd);
        return this;
    }

    public StationBuilder buildArrDate(LocalDate arrivalDate){
        this.station.setArrivalDate(arrivalDate);
        return this;
    }

    public StationBuilder buildArrTime(LocalTime arrTime) {
        this.station.setArrivalTime(arrTime);
        return this;
    }

    public StationBuilder buildDepTime(LocalTime depTime) {
        this.station.setDepartureTime(depTime);
        return this;
    }

    public StationBuilder buildRouteId(int routeId){
        this.station.setRouteId(routeId);
        return this;
    }

    public StationBuilder buildIntermediateId(int intermediateId){
        this.station.setIntermediateId(intermediateId);
        return this;
    }

    public StationBuilder buildArrDateTimeString(String arrDateTimeString) {
        this.station.setArrDateTimeString(arrDateTimeString);
        return this;
    }

    public StationBuilder buildDepTimeString(String depTimeString) {
        this.station.setDepTimeString(depTimeString);
        return this;
    }

    public StationBuilder buildId(int stationId) {
        this.station.setId(stationId);
        return this;
    }

    public StationBuilder buildName(String stationName) {
        this.station.setName(stationName);
        return this;
    }

    public StationBuilder buildStopping(int stopping) {
        this.station.setStopping(stopping);
        return this;
    }

    public Station build(){
        return station;
    }


}
