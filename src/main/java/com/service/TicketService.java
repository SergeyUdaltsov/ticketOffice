package com.service;

import com.entity.TicketOrder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 27.09.2018.
 */
public interface TicketService {

    List<Integer> getTicketCount(int routeId, int stationFrom, int stationTo) throws SQLException;

    void buyTickets(TicketOrder order) throws SQLException;


}
