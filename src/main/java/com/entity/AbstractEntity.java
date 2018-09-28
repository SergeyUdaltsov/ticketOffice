package com.entity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Serg on 23.09.2018.
 */
public class AbstractEntity {

    private int id;
    private String firstName;
    private String lastName;
    private boolean administrator;
    private boolean endStation = false;
    private String email;
    private String password;
    private String stationName;
    private String className;
    private int startStationId;
    private int finishStationId;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String code;
    private int intField1;
    private int intField2;
    private int intField3;
    private int intField4;
    private String stringField1;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int routeId;
    private String depStation;
    private String arrStation;
    private String tourTime;
    private int tourPrice;

    public boolean isEndStation() {
        return endStation;
    }

    public void setEndStation(boolean endStation) {
        this.endStation = endStation;
    }

    public String getTourTime() {
        return tourTime;
    }

    public int getTourPrice() {
        return tourPrice;
    }

    public void setTourTime(String tourTime) {
        this.tourTime = tourTime;
    }

    public void setTourPrice(int tourPrice) {
        this.tourPrice = tourPrice;
    }

    public String getDepStation() {
        return depStation;
    }

    public void setDepStation(String depStation) {
        this.depStation = depStation;
    }

    public String getArrStation() {
        return arrStation;
    }

    public void setArrStation(String arrStation) {
        this.arrStation = arrStation;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getIntField1() {
        return intField1;
    }

    public void setIntField1(int intField1) {
        this.intField1 = intField1;
    }

    public int getIntField2() {
        return intField2;
    }

    public void setIntField2(int intField2) {
        this.intField2 = intField2;
    }

    public int getIntField3() {
        return intField3;
    }

    public void setIntField3(int intField3) {
        this.intField3 = intField3;
    }

    public int getIntField4() {
        return intField4;
    }

    public void setIntField4(int intField4) {
        this.intField4 = intField4;
    }

    public String getStringField1() {
        return stringField1;
    }

    public void setStringField1(String stringField1) {
        this.stringField1 = stringField1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(int startStationId) {
        this.startStationId = startStationId;
    }

    public int getFinishStationId() {
        return finishStationId;
    }

    public void setFinishStationId(int finishStationId) {
        this.finishStationId = finishStationId;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
