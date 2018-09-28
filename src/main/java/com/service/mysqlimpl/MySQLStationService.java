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
}
