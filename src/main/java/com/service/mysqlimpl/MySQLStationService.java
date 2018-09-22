package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.Station;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;
/**
 * This is the MySQL implementation of {@code StationService interface}
 *
 */
public class MySQLStationService implements StationService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    /**
     * Adds new Station to the data base.
     *
     * @param station {@code Station} from {@code AddNewStationCommand} controller.
     * */
    @Override
    public void addNewStation(Station station) {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_STATION);

            statement.setString(1, station.getName());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
        LOGGER.info("Station " + station.getName() + " created");

    }

    /**
     * Checks if there is a Station with specified name in data base.
     *
     * @param stationName {@code String} from {@code addNewStation() method}
     * */
    @Override
    public boolean checkIfStationExists(String stationName) {
        boolean exists = false;

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_CHECK_IF_EXISTS_STATION);

            statement.setString(1, stationName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int count = resultSet.getInt(1);

                if (count != 0) {
                    exists = true;
                }
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return exists;
    }

    @Override
    public List<Station> getAllStations() {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<Station> stations = new ArrayList<>();

        try (Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_STATIONS)) {

            while (resultSet.next()) {

//                Client client = new ClientBuilder()
//                        .buildId(Integer.parseInt(resultSet.getString("client_id")))
//                        .buildFirstName(resultSet.getString("first_name"))
//                        .buildLastName(resultSet.getString("last_name"))
//                        .buildPhone(resultSet.getString("phone"))
//                        .build();
//
//                clients.add(client);
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }


        return null;
    }
}
