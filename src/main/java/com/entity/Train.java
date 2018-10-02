package com.entity;

import static com.utils.UtilConstants.*;

public class Train {

    private int id;
    private String name;
    private String nameRu;
    private int comfortPlacesCount;
    private int businessPlacesCount;
    private int economyPlacesCount;

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
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
