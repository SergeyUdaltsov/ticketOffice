package com.service;

import com.entity.Tour;
import com.entity.Train;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code TrainService} interface is responsible for processing business logic
 * with {@code Train} entity class
 */
public interface TrainService {

    /**
     * Responsible for creating new Train instance and save it to DB.
     *
     * @param train is {@code Train} instance to save.
     */
    void addNewTrain(Train train) throws SQLException;

    /**
     * Responsible for getting the list of all Train from DB.
     *
     * @return {@code List<Train>} the list of all Train from DB.
     */
    List<Train> getAllTrains();

    /**
     * Responsible for getting Train instance with specified id.
     *
     * @param trainId the {@code int} parameter specifies Train.
     * @return {@code Train} instance
     */
    Train getTrainById(int trainId);

    /**
     * Responsible for updating train with specified id with new values.
     *
     * @param train the {@code Train} instance encapsulating new values.
     */
    void updateTrain(Train train) throws SQLException;

    /**
     * Responsible for deleting specified Train from DB.
     *
     * @param trainId the {@code int} parameter specifies Train.
     */
    void deleteTrainById(int trainId) throws SQLException;

    /**
     * Responsible for getting the list of all trains
     * which go through between specified stations.
     *
     * @param departureStationId the {@code int} parameter specifies departure station.
     * @param arrivalStationId   the {@code int} parameter specifies arrival station.
     * @return {@code List<Tour>} list of tours between specified stations.
     */
    List<Tour> getTrainsByStations(int departureStationId, int arrivalStationId, LocalDate depDate);

}
