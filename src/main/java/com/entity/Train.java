package com.entity;

import static com.utils.UtilConstants.*;

public class Train {

    private int id;
    private String name;
    private int comfortPlacesCount;
    private int businessPlacesCount;
    private int economyPlacesCount;

    public Train(){
        this.businessPlacesCount = BUSINESS_PLACES_COUNT;
        this.comfortPlacesCount = COMFORT_PLACES_COUNT;
        this.economyPlacesCount = ECONOMY_PLACES_COUNT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComfortPlacesCount() {
        return comfortPlacesCount;
    }

    public void setComfortPlacesCount(int comfortPlacesCount) {
        this.comfortPlacesCount = comfortPlacesCount;
    }

    public int getBusinessPlacesCount() {
        return businessPlacesCount;
    }

    public void setBusinessPlacesCount(int businessPlacesCount) {
        this.businessPlacesCount = businessPlacesCount;
    }

    public int getEconomyPlacesCount() {
        return economyPlacesCount;
    }

    public void setEconomyPlacesCount(int economyPlacesCount) {
        this.economyPlacesCount = economyPlacesCount;
    }
}
