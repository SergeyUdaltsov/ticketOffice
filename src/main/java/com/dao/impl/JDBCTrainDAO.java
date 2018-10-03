package com.dao.impl;

import com.dao.CommonsOperable;
import com.dao.TrainDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code JDBCTrainDao} class is a JDBC implementation
 * of {@code TrainDAO} interface
 */
public class JDBCTrainDAO implements TrainDAO, CommonsOperable {

    /**
     * Responsible for retrieving count of available seats by train from DB
     *
     * @param trainId the{@code int} parameter specifies the corresponding Train.
     */
    @Override
    public List<Integer> getSeatsCountByTrainId(int trainId) throws SQLException {

        List<Integer> seats = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_SEATS_COUNT_BY_TRAIN_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, trainId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                seats.add(resultSet.getInt("economy"));
                seats.add(resultSet.getInt("business"));
                seats.add(resultSet.getInt("comfort"));

            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {
            throw new SQLException(COULD_NOT_LOAD_SEATS);
        }

        return seats;
    }

    /**
     * Responsible for saving new Train to DB
     *
     * @param train the instance of {@code Train} entity class
     */
    @Override
    public void addNewTrain(Train train) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_TRAIN)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, train.getName());
            statement.setString(2, train.getNameRu());
            statement.setInt(3, train.getEconomyPlacesCount());
            statement.setInt(4, train.getBusinessPlacesCount());
            statement.setInt(5, train.getComfortPlacesCount());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(TRAIN_EXISTS);
        }
    }

    /**
     * Responsible for deleting Train from DB
     *
     * @param trainId the {@code int} parameter, specifies Train.
     */
    @Override
    public void deleteTrainById(int trainId) throws SQLException {

        deleteItemById(trainId, SQL_DELETE_TRAIN_BY_ID);
    }

    /**
     * Responsible for getting the list of all Train from DB
     *
     * @param connection java.sql.Connection
     * */
    @Override
    public ResultSet getAllTrains(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_TRAINS);
    }

    /**
     * Responsible for getting ResultSet from DB containing all the data of Train.
     *
     * @param trainId the {@code int} parameter, specifies Train.
     * @param statement the {@code PreparedStatement} from {@code MySQLTrainService}.
     */
    @Override
    public ResultSet getTrainById(PreparedStatement statement, int trainId) throws SQLException {

        statement.setInt(1, trainId);

        return statement.executeQuery();
    }

    /**
     * Responsible for updating Train in DB
     *
     * @param train the instance of {@code Train} entity class
     */
    @Override
    public void updateTrain(Train train) throws SQLException {

        try(Connection connection = MySQLConnectorManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TRAIN)){

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, train.getName());
            statement.setString(2, train.getNameRu());
            statement.setInt(3, train.getEconomyPlacesCount());
            statement.setInt(4, train.getBusinessPlacesCount());
            statement.setInt(5, train.getComfortPlacesCount());
            statement.setInt(6, train.getId());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }
    }

    /**
     * Responsible for getting ResultSet from DB containing all the data of all the Train
     * due to the specified stations.
     *
     * @param departureStationId the {@code int} parameter, specifies departure station.
     * @param arrivalStationId the {@code int} parameter, specifies arrival station.
     * @param statement the {@code PreparedStatement} from {@code MySQLTrainService}.
     */
    @Override
    public ResultSet getTrainsByStations(PreparedStatement statement, int departureStationId,
                                         int arrivalStationId) throws SQLException {

        statement.setInt(1, departureStationId);
        statement.setInt(2, arrivalStationId);
        statement.setInt(3, departureStationId);
        statement.setInt(4, arrivalStationId);
        statement.setInt(5, departureStationId);
        statement.setInt(6, arrivalStationId);

        return statement.executeQuery();
    }
}
