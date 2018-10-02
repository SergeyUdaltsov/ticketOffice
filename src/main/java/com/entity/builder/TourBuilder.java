package com.entity.builder;

import com.entity.Tour;

/**
 * Created by Serg on 01.10.2018.
 */
public class TourBuilder {

    private Tour tour;

    public TourBuilder() {
        this.tour = new Tour();
    }

    public Tour build(){
        return this.tour;
    }

    public TourBuilder buildDepartureStationRu(String departureStationRu) {
        this.tour.setDepartureStationRu(departureStationRu);
        return this;
    }

    public TourBuilder buildArrivalStationRu(String arrivalStationRu) {
        this.tour.setArrivalStationRu(arrivalStationRu);
        return this;
    }

    public TourBuilder buildTourPrice(long tourPrice) {
        this.tour.setTourPrice(tourPrice);
        return this;
    }

    public TourBuilder buildTourTime(String tourTime){
        this.tour.setTourTime(tourTime);
        return this;
    }

    public TourBuilder buildTrain(String train){
        this.tour.setCode(train);
        return this;
    }

    public TourBuilder buildRouteId(int routeId) {
        this.tour.setRouteId(routeId);
        return this;
    }

    public TourBuilder buildArrivalDateTimeStart(String arrivalDateTimeStart){
        this.tour.setArrivalTimeDateStart(arrivalDateTimeStart);
        return this;
    }

    public TourBuilder buildDepartureTime(String departureTime){
        this.tour.setDepartureTime(departureTime);
        return this;
    }

    public TourBuilder buildDepartureStation(String departureStation){
        this.tour.setDepartureStation(departureStation);
        return this;
    }

    public TourBuilder buildDepartureStationId(int departureStationId) {
        this.tour.setDepartureStationId(departureStationId);
        return this;
    }

    public TourBuilder buildArrivalTimeDateFinish(String arrivalTimeDateFinish) {
        this.tour.setArrivalTimeDateFinish(arrivalTimeDateFinish);
        return this;
    }

    public TourBuilder buildArrivalStation(String arrivalStation){
        this.tour.setArrivalStation(arrivalStation);
        return this;
    }

    public TourBuilder buildArrivalStationId(int arrivalStationId) {
        this.tour.setArrivalStationId(arrivalStationId);
        return this;
    }
}
