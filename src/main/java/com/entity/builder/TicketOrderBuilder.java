package com.entity.builder;

import com.entity.Station;
import com.entity.TicketOrder;
import com.entity.User;

import java.time.LocalTime;

public class TicketOrderBuilder {

    private TicketOrder ticket;

    public TicketOrderBuilder() {
        this.ticket = new TicketOrder();
    }


    public TicketOrderBuilder buildEcoPrice(double ecoPr){
    this.ticket.setEcoPrice(ecoPr);
    return this;
    }

    public TicketOrderBuilder buildBusPrice(double busPr) {
        this.ticket.setBusPrice(busPr);
        return this;
    }

    public TicketOrderBuilder buildComPrice(double comPr) {
        this.ticket.setComPrice(comPr);
        return this;
    }

    public TicketOrderBuilder buildId(int ticketId) {
        this.ticket.setId(ticketId);
        return this;
    }

    public TicketOrderBuilder buildUser(User user){
        this.ticket.setUser(user);
        return this;
    }

    public TicketOrderBuilder buildEconomyCount(int economyCount){
        this.ticket.setCountOfEconomy(economyCount);
        return this;
    }

    public TicketOrderBuilder buildBusinessCount(int businessCount) {
        this.ticket.setCountOfBusiness(businessCount);
        return this;
    }

    public TicketOrderBuilder buildComfortCount(int comfortCount) {
        this.ticket.setCountOfComfort(comfortCount);
        return this;
    }

//    public TicketOrderBuilder buildTrainNumber(String trainNumber) {
//        this.ticket.setTrainNumber(trainNumber);
//        return this;
//    }

    public TicketOrderBuilder buildRouteId(int routeId) {
        this.ticket.setRouteId(routeId);
        return this;
    }

    public TicketOrderBuilder buildStationFrom(Station stationFrom) {
        this.ticket.setStationFrom(stationFrom);
        return this;
    }

    public TicketOrderBuilder buildStationTo(Station stationTo) {
        this.ticket.setStationTo(stationTo);
        return this;
    }

    public TicketOrderBuilder buildDepatureTime(LocalTime departureTime) {
        this.ticket.setDepartureTime(departureTime);
        return this;
    }

    public TicketOrderBuilder buildArrivalTime(LocalTime arrivalTime) {
        this.ticket.setDepartureTime(arrivalTime);
        return this;
    }

    public TicketOrder build() {
        return ticket;
    }
}
