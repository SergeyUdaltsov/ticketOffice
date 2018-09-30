package com.entity.builder;

import com.entity.Station;

public class StationBuilder {

    private Station station;

    public StationBuilder() {
        this.station = new Station();
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
