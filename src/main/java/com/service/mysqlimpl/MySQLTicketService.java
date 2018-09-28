package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.AbstractEntity;
import com.entity.builder.AbstractBuilder;
import com.service.TicketService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 27.09.2018.
 */
public class MySQLTicketService implements TicketService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    @Override
    public List<Integer> getTicketCount(int routeId, int stationFrom, int stationTo) throws SQLException {

        List<String> dateTimes = getDateTimeOfTrip(routeId, stationFrom, stationTo);

        List<Integer> availabeSeats = new ArrayList<>();

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {

            String query = "INSERT INTO dates (date_time, name) VALUES (?, ?);";

            PreparedStatement statement = connection.prepareStatement(query);

            LocalDateTime point = LocalDateTime.of(LocalDate.parse("2018-09-21"), LocalTime.parse("15:35"));

            statement.setString(1, point.toString());
            statement.setString(2, "from java");

            statement.executeUpdate();

//            PreparedStatement statement = connection.prepareStatement(SQL_COUNT_OF_AVAILABLE_SEATS);
//
//            statement.setString(1, dateTimes.get(0));
//            statement.setString(2, dateTimes.get(2));
//            statement.setString(3, dateTimes.get(1));
//            statement.setString(4, dateTimes.get(3));
//            statement.setInt(5, routeId);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//
//                int eco = resultSet.getInt("eco");
//                int bus = resultSet.getInt("bus");
//                int com = resultSet.getInt("com");
//
//                availabeSeats.add(eco);
//                availabeSeats.add(bus);
//                availabeSeats.add(com);
//
//            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return availabeSeats;
    }


    List<String> getDateTimeOfTrip(int routeId, int stationFrom, int stationTo) throws SQLException {
        List<String> dateTimes = new ArrayList<>();

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        PreparedStatement statement = connection.prepareStatement(SQL_GET_TIME_AND_DATE_OF_STATIONS_ID);

        statement.setInt(1, routeId);
        statement.setInt(2, stationFrom);
        statement.setInt(3, stationTo);
        statement.setInt(4, routeId);


        ResultSet resultSet = statement.executeQuery();

        String time = "";
        String date = "";

        while (resultSet.next()) {

            time = resultSet.getString("arrival_time");
            date = resultSet.getString("st_date");

            dateTimes.add(time);
            dateTimes.add(date);
        }

        MySQLConnectorManager.commitTransaction(connection);
        MySQLConnectorManager.closeConnection(connection);

        return dateTimes;

    }

    @Override
    public List<AbstractEntity> getIntermediateStationsByTrip(int routeId, int depStId, int arrStId) throws SQLException {

        List<String> dateTimes = getDateTimeOfTrip(routeId, depStId, arrStId);

        LocalDateTime departureDate = LocalDateTime.of(LocalDate.parse(dateTimes.get(1)), LocalTime.parse(dateTimes.get(0)));
        LocalDateTime arrivalDate = LocalDateTime.of(LocalDate.parse(dateTimes.get(3)), LocalTime.parse(dateTimes.get(2)));


        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<AbstractEntity> stations = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_STATIONS_IN_TRIP);

            statement.setString(1, dateTimes.get(1));
            statement.setString(2, dateTimes.get(3));
            statement.setInt(3, routeId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

              AbstractEntity station = new AbstractBuilder()
                      .buildArrStation(resultSet.getString("name"))
                      .buildArrTime(LocalTime.parse(resultSet.getString("arrival_time")))
                      .buildDepTime(LocalTime.parse(resultSet.getString("departure_time")))
                      .buildArrDate(LocalDate.parse(resultSet.getString("st_date")))
                      .build();

              stations.add(station);
            }

            stations = selectIntermediateStations(stations, departureDate, arrivalDate);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
        return stations;
    }


    private List<AbstractEntity> selectIntermediateStations(List<AbstractEntity> stations,
                                                    LocalDateTime departure, LocalDateTime arrival) {

        Iterator<AbstractEntity> iterator = stations.iterator();

        while (iterator.hasNext()){

            AbstractEntity station = iterator.next();

            LocalDateTime stDate = LocalDateTime.of(station.getArrivalDate(), station.getArrivalTime());

            if (stDate.isBefore(departure) || stDate.isAfter(arrival)){

                iterator.remove();
            }
        }

        return stations;
    }


    public static void main(String[] args) throws SQLException {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        String query = "INSERT INTO dates (date_time, name) VALUES (?, ?);";

       PreparedStatement statement = connection.prepareStatement(query);

       LocalDateTime point = LocalDateTime.of(LocalDate.parse("2018-09-21"), LocalTime.parse("15:35"));

       statement.setString(1, point.toString());
       statement.setString(2, "from java");

       statement.executeUpdate();


    }
}
