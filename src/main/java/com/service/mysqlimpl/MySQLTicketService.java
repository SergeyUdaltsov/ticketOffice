package com.service.mysqlimpl;

import com.dao.TicketDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Letter;
import com.entity.TicketOrder;
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
 * This is the MySQL implementation of {@code TicketService interface}
 */
public class MySQLTicketService implements TicketService {

    private final TicketDAO TICKET_DAO;
    private final StationService STATION_SERVICE;
    private final MailService MAIL_SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);


    public MySQLTicketService(TicketDAO ticketDAO, StationService stationService, MailService mailService) {
        this.TICKET_DAO = ticketDAO;
        this.STATION_SERVICE = stationService;
        this.MAIL_SERVICE = mailService;
    }

    /**
     * Responsible for getting the count of available tickets to the train
     * which goes through specified stations.
     *
     * @param routeId the {@code int} parameter specifies the Route.
     * @param stationFrom the {@code int} parameter specifies departure station.
     * @param stationTo the {@code int} parameter specifies arrival station.
     * @return {@code List<Integer>} list of values according to types.
     * */
    @Override
    public List<Integer> getTicketCount(int routeId, int stationFrom, int stationTo) throws SQLException {

        List<String> points = STATION_SERVICE.getDateTimeOfTrip(routeId, stationFrom, stationTo);

        List<Integer> availableSeats = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_OF_AVAILABLE_SEATS)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TICKET_DAO.getCountOfAvailableSeats(statement, points, routeId);

            availableSeats = getCountOfSeatsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TICKETS);

        }

        return availableSeats;
    }

    /**
     * Responsible for creating {@code List<Integer>} from ResultSet
     *
     * @param resultSet the {@code ResultSet} from {@code getTicketCount()} method.
     * @return {@code List<Integer>} list of counts of tickets by the types.
     * */
    private List<Integer> getCountOfSeatsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Integer> availableSeats = new ArrayList<>();

        while (resultSet.next()) {

            int eco = resultSet.getInt("eco");
            int bus = resultSet.getInt("bus");
            int com = resultSet.getInt("com");

            availableSeats.add(eco);
            availableSeats.add(bus);
            availableSeats.add(com);

        }

        return availableSeats;
    }

    /**
     * Responsible for process of buying tickets which consists of
     * decreasing the values of corresponds available tickets in DB.
     *
     * @param order the {@code Order} instance encapsulating all the data for buying tickets.
     * */
    @Override
    public void buyTickets(TicketOrder order) throws SQLException {


        List<String> dateTimes = STATION_SERVICE.getDateTimeOfTrip(order.getRouteId(),
                order.getStationFrom().getId(), order.getStationTo().getId());

        try {

            TICKET_DAO.buyTickets(order, dateTimes);

            LOGGER.info(USER + order.getUser().getFirstName() + " " + order.getUser().getLastName() + BOUGHT_TICKETS);

            sendMail(order);

        } catch (SQLException e) {

            throw new SQLException(NOT_ENOUGH_TICKETS);

        }
    }

    /**
     * Responsible for sending mail to the user who bought tickets.
     *
     * @param order the {@code TicketOrder} instance encapsulating all the data for sending mail.
     * */
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

        LOGGER.info(MAIL_SENT);
    }


}
