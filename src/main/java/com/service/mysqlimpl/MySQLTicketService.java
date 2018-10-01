package com.service.mysqlimpl;

import com.dao.factory.DAOFactory;
import com.dao.impl.JDBCStationDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.AbstractEntity;
import com.entity.Letter;
import com.entity.TicketOrder;
import com.entity.builder.AbstractBuilder;
import com.entity.builder.LetterBuilder;
import com.service.MailService;
import com.service.StationService;
import com.service.TicketService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 27.09.2018.
 */
public class MySQLTicketService implements TicketService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);
    private static final MailService MAIL_SERVICE = DAOFactory.getDAOFactory().getMailService();
    private static final StationService STATION_SERVICE = DAOFactory.getDAOFactory().getStationService(new JDBCStationDAO());

    @Override
    public List<Integer> getTicketCount(int routeId, int stationFrom, int stationTo) throws SQLException {

        List<String> points = STATION_SERVICE.getDateTimeOfTrip(routeId, stationFrom, stationTo);

        List<Integer> availableSeats = new ArrayList<>();

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_COUNT_OF_AVAILABLE_SEATS);

            statement.setString(1, points.get(0));
            statement.setString(2, points.get(1));
            statement.setInt(3, routeId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int eco = resultSet.getInt("eco");
                int bus = resultSet.getInt("bus");
                int com = resultSet.getInt("com");

                availableSeats.add(eco);
                availableSeats.add(bus);
                availableSeats.add(com);

            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return availableSeats;
    }

    @Override
    public List<AbstractEntity> getIntermediateStationsByTrip(int routeId, int depStId, int arrStId) throws SQLException {

        List<String> dateTimes = STATION_SERVICE.getDateTimeOfTrip(routeId, depStId, arrStId);

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<AbstractEntity> stations = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_STATIONS_IN_TRIP);

            statement.setString(1, dateTimes.get(0));
            statement.setString(2, dateTimes.get(1));
            statement.setInt(3, routeId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AbstractEntity station = new AbstractBuilder()
                        .buildArrStation(resultSet.getString("name"))
                        .buildArrTimeDateString(resultSet.getString("arrival_date_time"))
                        .buildDepTimeString(resultSet.getString("departure_time"))
                        .build();

                stations.add(station);
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
        return stations;
    }


    @Override
    public void buyTickets(TicketOrder order) throws SQLException {


        List<String> dateTimes = STATION_SERVICE.getDateTimeOfTrip(order.getRouteId(),
                order.getStationFrom().getId(), order.getStationTo().getId());

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_BUY_TICKETS);

            statement.setInt(1, order.getCountOfEconomy());
            statement.setInt(2, order.getCountOfBusiness());
            statement.setInt(3, order.getCountOfComfort());
            statement.setInt(4, order.getRouteId());
            statement.setString(5, dateTimes.get(0));
            statement.setString(6, dateTimes.get(1));

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

            sendMail(order);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }


    }

    private void sendMail(TicketOrder order) {

        StringBuilder text = new StringBuilder();

        text.append("Dear ").append(order.getUser().getFirstName()).append(" ").append(order.getUser().getLastName()).append("!")
                .append(System.lineSeparator()).append("You have bought ticket(s) on our site. Now you can travel ")
                .append(System.lineSeparator()).append("with comfort by our trains").append(System.lineSeparator())
                .append("Economy - ").append(order.getCountOfEconomy()).append(System.lineSeparator())
                .append("Business - ").append(order.getCountOfBusiness()).append(System.lineSeparator())
                .append("Comfort - ").append(order.getCountOfComfort());


        Letter letter = new LetterBuilder()
                .buildAddress(order.getUser().getEmail())
                .buildSubject("Mail from ticket office")
                .buildText(text.toString())
                .build();

        MAIL_SERVICE.sendMail(letter);
    }


}
