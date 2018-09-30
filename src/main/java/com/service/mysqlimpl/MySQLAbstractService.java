package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.AbstractEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 23.09.2018.
 */
abstract class MySQLAbstractService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLAbstractService.class);

    void updateItem(AbstractEntity entity, String query) throws SQLException {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        PreparedStatement statement = connection.prepareStatement(query);

        try {

            switch (entity.getClassName()) {

                case STATION: {
                    statement.setString(1, entity.getStationName());
                    statement.setInt(2, entity.getId());
                    statement.executeUpdate();

                    MySQLConnectorManager.commitTransaction(connection);

                    break;
                }
                case USER: {
                    break;
                }
                case TRAIN: {
                    statement.setString(1, entity.getStringField1());
                    statement.setInt(2, entity.getIntField2());
                    statement.setInt(3, entity.getIntField3());
                    statement.setInt(4, entity.getIntField4());
                    statement.setInt(5, entity.getIntField1());

                    statement.executeUpdate();
                    break;
                }

                default: {
                    MySQLConnectorManager.rollbackTransaction(connection);

                    throw new SQLException(UNKNOWN_ENTITY);
                }

            }

            MySQLConnectorManager.commitTransaction(connection);
        } catch (SQLException e) {
            throw new SQLException(STATION_EXISTS);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
    }


    int addNewItem(AbstractEntity entity, String query) throws SQLException {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        int id = 0;

        switch (entity.getClassName()) {

            case USER: {
                try {
                    statement.setString(1, entity.getFirstName());
                    statement.setString(2, entity.getLastName());
                    statement.setString(3, entity.getEmail());
                    statement.setString(4, entity.getPassword());
                    statement.setBoolean(5, false);
                    statement.executeUpdate();

                    MySQLConnectorManager.commitTransaction(connection);

                } catch (SQLException e) {

                    throw new SQLException(USER_EXISTS);

                } finally {
                    MySQLConnectorManager.closeConnection(connection);
                }

                break;
            }
            case ROUTE: {
                try {
                    statement.setString(1, entity.getCode());
                    statement.setInt(2, entity.getStartStationId());
                    statement.setString(3, entity.getDepartureTime().toString());
                    statement.setString(4, entity.getDepartureDate().toString());
                    statement.setString(5, entity.getArrivalDate().toString());
                    statement.setInt(6, entity.getFinishStationId());
                    statement.setString(7, entity.getArrivalTime().toString());

                    statement.executeUpdate();

                    ResultSet rs = statement.getGeneratedKeys();

                    if (rs.next()) {
                        id = rs.getInt(1);
                    }

                    MySQLConnectorManager.commitTransaction(connection);

                } catch (SQLException e) {
                    throw new SQLException(ROUTE_EXISTS);

                } finally {
                    MySQLConnectorManager.closeConnection(connection);
                }
                break;
            }
            case STATION: {
                try {
                    statement.setString(1, entity.getStationName());

                    statement.executeUpdate();

                    MySQLConnectorManager.commitTransaction(connection);

                } catch (SQLException e) {

                    throw new SQLException(STATION_EXISTS);

                } finally {
                    MySQLConnectorManager.closeConnection(connection);
                }
                break;
            }
            case INTER_STATION: {
                try {

                    LocalDateTime arrDateTime = LocalDateTime.of(entity.getArrivalDate(), entity.getArrivalTime());
                    LocalDateTime depDateTime = entity.getDepartureTime().atDate(entity.getArrivalDate());

                    int stopping = (int)arrDateTime.until(depDateTime, ChronoUnit.MINUTES);

                    statement.setInt(1, entity.getRouteId());
                    statement.setInt(2, entity.getStartStationId());
                    statement.setString(3, arrDateTime.toString());
                    statement.setInt(4, stopping);
                    statement.setString(5, entity.getDepartureTime().toString());

                    statement.executeUpdate();

                    MySQLConnectorManager.commitTransaction(connection);

                } catch (SQLException e) {

                    throw new SQLException(INTERMEDIATE_STATION_ERROR);

                } finally {
                    MySQLConnectorManager.closeConnection(connection);
                }

                break;
            }
            case TRAIN: {
                try {
                    statement.setString(1, entity.getStringField1());
                    statement.setInt(2, entity.getIntField1());
                    statement.setInt(3, entity.getIntField2());
                    statement.setInt(4, entity.getIntField3());

                    statement.executeUpdate();

                    MySQLConnectorManager.commitTransaction(connection);

                } catch (SQLException e) {

                    throw new SQLException(TRAIN_EXISTS);
                } finally {
                    MySQLConnectorManager.closeConnection(connection);
                }

                break;
            }
            default: {
                MySQLConnectorManager.rollbackTransaction(connection);
                MySQLConnectorManager.closeConnection(connection);

                throw new SQLException(UNKNOWN_ENTITY);
            }
        }
        return id;
    }


    void deleteItem(int itemId, String query) throws SQLException {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, itemId);

        statement.executeUpdate();

        MySQLConnectorManager.commitTransaction(connection);


    }

}
