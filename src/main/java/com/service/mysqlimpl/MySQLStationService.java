package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.AbstractEntity;
import com.entity.Station;
import com.entity.builder.AbstractBuilder;
import com.entity.builder.StationBuilder;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * This is the MySQL implementation of {@code StationService interface}
 */
public class MySQLStationService extends MySQLAbstractService implements StationService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    /**
     * Adds new Station to the data base.
     *
     * @param station {@code Station} from {@code AddNewStationCommand} controller.
     */
    @Override
    public void addNewStation(Station station) throws SQLException {

        AbstractEntity newStation = new AbstractBuilder()
                .buildStationName(station.getName())
                .buildClass(station.getClass().getSimpleName())
                .build();

        addNewItem(newStation, SQL_ADD_NEW_STATION);

        LOGGER.info(STATION + station.getName() + CREATED);

    }

    public void deleteStationById(int stationId) throws SQLException {

        deleteItem(stationId, SQL_DELETE_STATION);
    }

    /**
     * Gets all the Station from data base.
     *
     * @return {@code List<Station>} all the Station stored in data base.
     */
    @Override
    public List<Station> getAllStations() {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<Station> stations = new ArrayList<>();

        try (Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_STATIONS)) {

            while (resultSet.next()) {

                Station station = new StationBuilder()
                        .buildName(resultSet.getString("name"))
                        .buildId(resultSet.getInt("station_id"))
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
    public Station getStationById(int stationId) {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        Station station = null;

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_STATION_BY_ID);

            statement.setInt(1, stationId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                station = new StationBuilder()
                        .buildId(resultSet.getInt("station_id"))
                        .buildName(resultSet.getString("name"))
                        .build();
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return station;
    }

    @Override
    public void updateStation(Station station) throws SQLException {

        AbstractEntity stationUpdate = new AbstractBuilder()
                .buildStationName(station.getName())
                .buildId(station.getId())
                .buildClass(station.getClass().getSimpleName())
                .build();

        updateItem(stationUpdate, SQL_UPDATE_STATION);

        LOGGER.info(STATION + station.getName() + UPDATED);
    }

    @Override
    public List<Station> getIntermediateStationsByTrip(int routeId, int depStId, int arrStId) throws SQLException {

        List<String> dateTimes = getDateTimeOfTrip(routeId, depStId, arrStId);

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<Station> stations = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_STATIONS_IN_TRIP);

            statement.setString(1, dateTimes.get(0));
            statement.setString(2, dateTimes.get(1));
            statement.setInt(3, routeId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Station station = new StationBuilder()
                        .buildName(resultSet.getString("name"))
                        .buildArrDateTimeString(resultSet.getString("arrival_date_time"))
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
    public List<String> getDateTimeOfTrip(int routeId, int stationFrom, int stationTo) throws SQLException {

        List<String> dateTimes = new ArrayList<>();

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        PreparedStatement statement = connection.prepareStatement(SQL_GET_TIME_AND_DATE_OF_STATIONS_ID);

        statement.setInt(1, routeId);
        statement.setInt(2, stationFrom);
        statement.setInt(3, routeId);
        statement.setInt(4, stationTo);


        ResultSet resultSet = statement.executeQuery();

        String point = "";

        while (resultSet.next()) {

            point = resultSet.getString("arrival_date_time");

            dateTimes.add(point);
        }

        MySQLConnectorManager.commitTransaction(connection);
        MySQLConnectorManager.closeConnection(connection);

        return dateTimes;
    }
}
