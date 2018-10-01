package com.dao;

import com.entity.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 01.10.2018.
 */
public interface TrainDAO {

    List<Integer> getSeatsCountByTrainId(int trainId) throws SQLException;

    void addNewTrain(Train train) throws SQLException;

    void deleteTrainById(int trainId) throws SQLException;

    ResultSet getAllTrains(Connection connection) throws SQLException;

    ResultSet getTrainById(PreparedStatement statement, int trainId) throws SQLException;

    ResultSet getTrainsByStations(PreparedStatement statement, int departureStation, int arrivalStation) throws SQLException;

    void updateTrain(Train train) throws SQLException;
}
