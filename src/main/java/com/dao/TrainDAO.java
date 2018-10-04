package com.dao;

import com.entity.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code TrainDao} interface is responsible for
 * connecting {@code Train} entity class to DB
 */
public interface TrainDAO {

    /**
     * Responsible for retrieving count of available seats by train from DB
     *
     * @param trainId the{@code int} parameter specifies the corresponding Train.
     */
    List<Integer> getSeatsCountByTrainId(int trainId) throws SQLException;

    /**
     * Responsible for saving new Train to DB
     *
     * @param train the instance of {@code Train} entity class
     */
    void addNewTrain(Train train) throws SQLException;

    /**
     * Responsible for deleting Train from DB
     *
     * @param trainId the {@code int} parameter, specifies Train.
     */
    void deleteTrainById(int trainId) throws SQLException;

    /**
     * Responsible for getting the list of all Train from DB
     *
     * @param connection java.sql.Connection
     * */
    ResultSet getAllTrains(Connection connection) throws SQLException;

    /**
     * Responsible for getting ResultSet from DB containing all the data of Train.
     *
     * @param trainId the {@code int} parameter, specifies Train.
     * @param statement the {@code PreparedStatement} from {@code MySQLTrainService}.
     */
    ResultSet getTrainById(PreparedStatement statement, int trainId) throws SQLException;

    /**
     * Responsible for getting ResultSet from DB containing all the data of all the Train
     * due to the specified stations.
     *
     * @param departureStation the {@code int} parameter, specifies departure station.
     * @param arrivalStation the {@code int} parameter, specifies arrival station.
     * @param statement the {@code PreparedStatement} from {@code MySQLTrainService}.
     */
    ResultSet getTrainsByStations(PreparedStatement statement, int departureStation,
                                  int arrivalStation, LocalDate depDate) throws SQLException;

    /**
     * Responsible for updating Train in DB
     *
     * @param train the instance of {@code Train} entity class
     */
    void updateTrain(Train train) throws SQLException;

    int getIdOfFirstTrainInDataBase() throws SQLException;
}
