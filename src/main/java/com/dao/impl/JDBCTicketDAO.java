package com.dao.impl;

import com.dao.TicketDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.TicketOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 01.10.2018.
 */
public class JDBCTicketDAO implements TicketDAO {

    @Override
    public void buyTickets(TicketOrder order, List<String> dateTimes) throws SQLException {

        try(Connection connection = MySQLConnectorManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_BUY_TICKETS)){

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, order.getCountOfEconomy());
            statement.setInt(2, order.getCountOfBusiness());
            statement.setInt(3, order.getCountOfComfort());
            statement.setInt(4, order.getRouteId());
            statement.setString(5, dateTimes.get(0));
            statement.setString(6, dateTimes.get(1));

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }
    }

    @Override
    public ResultSet getCountOfAvailableSeats(PreparedStatement statement, List<String> points,
                                              int routeId) throws SQLException {

        statement.setString(1, points.get(0));
        statement.setString(2, points.get(1));
        statement.setInt(3, routeId);

        return statement.executeQuery();
    }
}
