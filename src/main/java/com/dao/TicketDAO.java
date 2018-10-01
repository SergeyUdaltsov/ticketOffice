package com.dao;

import com.entity.TicketOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 01.10.2018.
 */
public interface TicketDAO {

    void buyTickets(TicketOrder order, List<String> dateTimes) throws SQLException;

    ResultSet getCountOfAvailableSeats(PreparedStatement statement, List<String> points, int routeId) throws SQLException;
}
