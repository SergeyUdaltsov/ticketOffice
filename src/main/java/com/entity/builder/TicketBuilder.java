package com.entity.builder;

import com.entity.Station;
import com.entity.Ticket;
import com.entity.TicketClass;

import java.time.LocalTime;

public class TicketBuilder {

    private Ticket ticket;

    public TicketBuilder() {
        this.ticket = new Ticket();
    }

    public TicketBuilder buildId(int ticketId) {
        this.ticket.setId(ticketId);
        return this;
    }

    public TicketBuilder buildPassengerFirstName(String passengerFirstName) {
        this.ticket.setPassengerFirstName(passengerFirstName);
        return this;
    }

    public TicketBuilder buildPassengerLastName(String passengerLastName) {
        this.ticket.setPassengerLastName(passengerLastName);
        return this;
    }

    public TicketBuilder buildTrainNumber(String trainNumber) {
        this.ticket.setTrainNumber(trainNumber);
        return this;
    }

    public TicketBuilder buildTicketClass(TicketClass ticketClass) {
        this.ticket.setTicketClass(ticketClass);
        return this;
    }

    public TicketBuilder buildSeatNumber(int seatNumber) {
        this.ticket.setSeatNumber(seatNumber);
        return this;
    }

    public TicketBuilder buildStationFrom(Station stationFrom) {
        this.ticket.setStationFrom(stationFrom);
        return this;
    }

    public TicketBuilder buildStationTo(Station stationTo) {
        this.ticket.setStationTo(stationTo);
        return this;
    }

    public TicketBuilder buildDepatureTime(LocalTime departureTime) {
        this.ticket.setDepartureTime(departureTime);
        return this;
    }

    public TicketBuilder buildArrivalTime(LocalTime arrivalTime) {
        this.ticket.setDepartureTime(arrivalTime);
        return this;
    }
}
