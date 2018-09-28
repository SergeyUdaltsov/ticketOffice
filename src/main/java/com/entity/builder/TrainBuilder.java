package com.entity.builder;

import com.entity.Train;

public class TrainBuilder {

    private Train train;

    public TrainBuilder() {
        this.train = new Train();
    }

    public TrainBuilder buildId(int id) {
        this.train.setId(id);
        return this;
    }

    public TrainBuilder buildName(String name) {
        this.train.setName(name);
        return this;
    }

    public TrainBuilder buildComfort(int comfortPlaces) {
        this.train.setComfortPlacesCount(comfortPlaces);
        return this;
    }

    public TrainBuilder buildEconomy(int economyPlaces) {
        this.train.setEconomyPlacesCount(economyPlaces);
        return this;
    }

    public TrainBuilder buildBusiness(int businessPlaces) {
        this.train.setBusinessPlacesCount(businessPlaces);
        return this;
    }

    public Train build() {
        return this.train;
    }


}
