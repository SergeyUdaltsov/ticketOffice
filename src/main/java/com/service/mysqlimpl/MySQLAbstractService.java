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

                    break;
                }
                case USER: {
                    break;
                }
                case TRAIN: {

                    fillUpTrain(entity, statement);
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
            throw new SQLException(COULD_NOT_UPDATE_ITEM);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
            statement.close();
        }
    }


    int addNewItem(AbstractEntity entity, String query) throws SQLException {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        int id = 0;
        try {
            switch (entity.getClassName()) {
                case USER: {

                    statement.setString(1, entity.getFirstName());
                    statement.setString(2, entity.getLastName());
                    statement.setString(3, entity.getEmail());
                    statement.setString(4, entity.getPassword());
                    statement.setBoolean(5, false);
                    statement.executeUpdate();

                    break;
                }
                case ROUTE: {
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

                    break;
                }
                case STATION: {

                    statement.setString(1, entity.getStationName());

                    statement.executeUpdate();

                    LOGGER.info(STATION + " " + entity.getStationName() + CREATED);

                    break;
                }
                case INTER_STATION: {

                    LocalDateTime arrDateTime = LocalDateTime.of(entity.getArrivalDate(), entity.getArrivalTime());
                    LocalDateTime depDateTime = entity.getDepartureTime().atDate(entity.getArrivalDate());

                    int stopping = (int) arrDateTime.until(depDateTime, ChronoUnit.MINUTES);

                    statement.setInt(1, entity.getRouteId());
                    statement.setInt(2, entity.getStartStationId());
                    statement.setString(3, arrDateTime.toString());
                    statement.setInt(4, stopping);
                    statement.setString(5, entity.getDepartureTime().toString());

                    statement.executeUpdate();

                    break;
                }
                case TRAIN: {

                    statement.setString(1, entity.getStringField1());
                    statement.setInt(2, entity.getIntField1());
                    statement.setInt(3, entity.getIntField2());
                    statement.setInt(4, entity.getIntField3());

                    statement.executeUpdate();

                    break;
                }
                default: {
                    MySQLConnectorManager.rollbackTransaction(connection);
                    MySQLConnectorManager.closeConnection(connection);

                    throw new SQLException(UNKNOWN_ENTITY);
                }
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_ADD_NEW_ITEM);

        }finally {

            MySQLConnectorManager.closeConnection(connection);
            statement.close();
        }
        return id;
    }


    void deleteItemById(int itemId, String query) throws SQLException {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, itemId);

        statement.executeUpdate();

        MySQLConnectorManager.commitTransaction(connection);

        MySQLConnectorManager.closeConnection(connection);
        statement.close();
    }

    private void fillUpTrain(AbstractEntity entity, PreparedStatement statement) throws SQLException {

        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getCountOfEconomy());
        statement.setInt(3, entity.getCountOfBusiness());
        statement.setInt(4, entity.getCountOfComfort());
        statement.setInt(5, entity.getId());
    }

}
