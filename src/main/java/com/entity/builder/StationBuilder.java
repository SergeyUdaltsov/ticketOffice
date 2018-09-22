package com.entity.builder;

import com.entity.Station;

public class StationBuilder {

    private Station station;

    public StationBuilder() {
        this.station = new Station();
    }

    public StationBuilder buildId(int stationId) {
        this.station.setId(stationId);
        return this;
    }

    public StationBuilder buildName(String stationName) {
        this.station.setName(stationName);
        return this;
    }

    public Station build(){
        return station;
    }


}
