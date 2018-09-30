package com.entity;

public class Station implements Sendable{

    private int id;
    private String name;
    private int intermediateId;
    private String arrDateTimeString;
    private String depTimeString;
    private int stopping;


    public int getStopping() {
        return stopping;
    }

    public void setStopping(int stopping) {
        this.stopping = stopping;
    }

    public int getIntermediateId() {
        return intermediateId;
    }

    public void setIntermediateId(int intermediateId) {
        this.intermediateId = intermediateId;
    }

    public String getArrDateTimeString() {
        return arrDateTimeString;
    }

    public void setArrDateTimeString(String arrDateTimeString) {
        this.arrDateTimeString = arrDateTimeString;
    }

    public String getDepTimeString() {
        return depTimeString;
    }

    public void setDepTimeString(String depTimeString) {
        this.depTimeString = depTimeString;
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
}
