package com.service;

import com.entity.TicketOrder;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code TicketService} interface is responsible for processing business logic
 * with {@code Ticket} entity class
 */
public interface TicketService {

    /**
     * Responsible for getting the count of available tickets to the train
     * which goes through specified stations.
     *
     * @param routeId the {@code int} parameter specifies the Route.
     * @param stationFrom the {@code int} parameter specifies departure station.
     * @param stationTo the {@code int} parameter specifies arrival station.
     * @return {@code List<Integer>} list of values according to types.
     * */
    List<Integer> getTicketCount(int routeId, int stationFrom, int stationTo) throws SQLException;

    /**
     * Responsible for process of buying tickets which consists of
     * decreasing the values of corresponds available tickets in DB.
     *
     * @param order the {@code Order} instance encapsulating all the data for buying tickets.
     * */
    void buyTickets(TicketOrder order) throws SQLException;


}
