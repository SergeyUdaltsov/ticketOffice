package com.entity.builder;

import com.entity.AbstractEntity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Serg on 23.09.2018.
 */
public class AbstractBuilder {

    private AbstractEntity entity;

    public AbstractBuilder() {
        this.entity = new AbstractEntity();
    }

    public AbstractBuilder buildDepStation(String station) {
        this.entity.setDepStation(station);
        return this;
    }

    public AbstractBuilder buildArrStation(String station) {
        this.entity.setArrStation(station);
        return this;
    }

    public AbstractBuilder buildRouteId(int routeId) {
        this.entity.setRouteId(routeId);
        return this;
    }

    public AbstractBuilder buildArrDate(LocalDate date) {
        this.entity.setArrivalDate(date);
        return this;
    }

    public AbstractBuilder buildDepDate(LocalDate date) {
        this.entity.setDepartureDate(date);
        return this;
    }

    public AbstractBuilder buildStringField1(String field1) {
        this.entity.setStringField1(field1);
        return this;
    }

    public AbstractBuilder buildIntField4(int field4) {
        this.entity.setIntField4(field4);
        return this;
    }

    public AbstractBuilder buildIntField1(int field1) {
        this.entity.setIntField1(field1);
        return this;
    }

    public AbstractBuilder buildIntField2(int field2) {
        this.entity.setIntField2(field2);
        return this;
    }

    public AbstractBuilder buildIntField3(int field3) {
        this.entity.setIntField3(field3);
        return this;
    }

    public AbstractBuilder buildCode(String code) {
        this.entity.setCode(code);
        return this;
    }

    public AbstractBuilder buildFinishStationId(int finishStationId) {
        this.entity.setFinishStationId(finishStationId);
        return this;
    }

    public AbstractBuilder buildArrTime(LocalTime arrivalTime) {
        this.entity.setArrivalTime(arrivalTime);
        return this;
    }

    public AbstractBuilder buildDepTime(LocalTime departureTime) {
        this.entity.setDepartureTime(departureTime);
        return this;
    }

    public AbstractBuilder buildStartStationId(int startStationId) {
        this.entity.setStartStationId(startStationId);
        return this;
    }

    public AbstractBuilder buildId(int id) {
        this.entity.setId(id);
        return this;
    }

    public AbstractBuilder buildFirstName(String firstName) {
        this.entity.setFirstName(firstName);
        return this;
    }

    public AbstractBuilder buildLastName(String lastName) {
        this.entity.setLastName(lastName);
        return this;
    }

    public AbstractBuilder buildEmail(String email) {
        this.entity.setEmail(email);
        ;
        return this;
    }

    public AbstractBuilder buildPassword(String password) {
        this.entity.setPassword(password);
        return this;
    }

    public AbstractBuilder buildAdmin(boolean admin) {
        this.entity.setAdministrator(admin);
        return this;
    }

    public AbstractBuilder buildStationName(String stationName) {
        this.entity.setStationName(stationName);
        return this;
    }

    public AbstractBuilder buildClass(String classname) {
        this.entity.setClassName(classname);
        return this;
    }

    public AbstractEntity build() {
        return entity;
    }

    public AbstractBuilder buildEndStation(boolean isEndStation) {
        this.entity.setEndStation(isEndStation);
        return this;
    }
}
