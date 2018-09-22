package com.entity.builder;

import com.entity.Rout;
import com.entity.Station;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class RoutBuilder {

    private Rout rout;

    public RoutBuilder() {
        this.rout = new Rout();
    }

    public RoutBuilder buildId(int routId) {
        this.rout.setId(routId);
        return this;
    }

    public RoutBuilder buildStartStation(Station startStation, LocalTime departureTime) {
        this.rout.setStartStation(startStation);
        this.rout.setDepartureTime(departureTime);
        return this;
    }

    public RoutBuilder buildFinishStation(Station finishStation, LocalTime arrivalTime) {
        this.rout.setFinishStation(finishStation);
        this.rout.setArrivalTime(arrivalTime);
        return this;
    }

    public RoutBuilder buildIntermediateStation(Map<Station, List<LocalTime>> intermediateStations) {
        this.rout.setIntermediateStations(intermediateStations);
        return this;
    }
}
