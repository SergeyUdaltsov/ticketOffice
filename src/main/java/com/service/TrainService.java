package com.service;

import com.entity.Tour;
import com.entity.Train;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serg on 25.09.2018.
 */
public interface TrainService {

    void addNewTrain(Train train) throws SQLException;
    List<Train> getAllTrains();
    Train getTrainById(int trainId);
    void updateTrain(Train train) throws SQLException;
    void deleteTrainById(int trainId) throws SQLException;
    List<Tour> getTrainsByStations(int departureStationId, int arrivalStationId);

}
