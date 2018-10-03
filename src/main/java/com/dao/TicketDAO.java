package com.dao;

import com.entity.TicketOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The {@code TicketDao} interface is responsible for
 * connecting {@code Ticket} entity class to DB
 */
public interface TicketDAO {

    /**
     * Responsible for buying tickets process performing.
     *
     * @param order the {@code TicketOrder} instance containing all the data about tickets to buy.
     * @param dateTimes the {@code List<String>} instance containing the data about dates and times of tour.
     * */
    void buyTickets(TicketOrder order, List<String> dateTimes) throws SQLException;

    /**
     * Responsible for getting the ResultSet from DB containing the data of count of available tickets of Tour.
     *
     * @param routeId the {@code int} parameter, specifies route.
     * @param points the {@code List<String>} instance containing the data about dates and times of tour.
     * @param statement the {@code PreparedStatement} from {@code MySQLTicketService}.
     * */
    ResultSet getCountOfAvailableSeats(PreparedStatement statement, List<String> points, int routeId) throws SQLException;
}
