package com.service.mysqlimpl;

import com.dao.TrainDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Tour;
import com.entity.Train;
import com.entity.builder.TourBuilder;
import com.entity.builder.TrainBuilder;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * This is the MySQL implementation of {@code TrainService interface}
 */
public class MySQLTrainService implements TrainService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLTrainService.class);

    private final TrainDAO TRAIN_DAO;

    public MySQLTrainService(TrainDAO trainDAO) {
        this.TRAIN_DAO = trainDAO;
    }

    @Override
    public void addNewTrain(Train train) throws SQLException {

        TRAIN_DAO.addNewTrain(train);
    }


    @Override
    public Train getTrainById(int trainId) {

        Train train = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TRAIN_BY_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TRAIN_DAO.getTrainById(statement, trainId);

            while (resultSet.next()) {

                train = getTrainFromResultSet(resultSet);
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TRAINS);

        }
        return train;
    }

    @Override
    public void updateTrain(Train train) throws SQLException {

        TRAIN_DAO.updateTrain(train);
    }

    @Override
    public void deleteTrainById(int trainId) throws SQLException {
        TRAIN_DAO.deleteTrainById(trainId);
    }

    @Override
    public List<Tour> getTrainsByStations(int departureStationId, int arrivalStationId) {

        List<Tour> tours = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SHOW_TRAINS_BY_STATIONS)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TRAIN_DAO.getTrainsByStations(statement, departureStationId, arrivalStationId);

            tours = getToursFromResultSet(resultSet);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_ROUTES);
        }
        return tours;
    }

    private List<Tour> getToursFromResultSet(ResultSet resultSet) throws SQLException {

        List<Tour> tours = new ArrayList<>();

        while (resultSet.next()) {

            Tour tour = new TourBuilder()
                    .buildTrain(resultSet.getString("train"))
                    .buildRouteId(resultSet.getInt("route_id"))
                    .buildArrivalDateTimeStart(resultSet.getString("arr_date_from"))
                    .buildDepartureTime(resultSet.getString("dep_time"))
                    .buildDepartureStation(resultSet.getString("dep_st"))
                    .buildDepartureStationId(resultSet.getInt("dep_st_id"))
                    .buildArrivalTimeDateFinish(resultSet.getString("arr_date_to"))
                    .buildArrivalStation(resultSet.getString("arr_st"))
                    .buildArrivalStationId(resultSet.getInt("arr_st_id"))
                    .build();

            LocalDateTime departure = LocalDateTime.parse(tour.getArrivalTimeDateStart().replace(" ", "T"));
            LocalDateTime arrival = LocalDateTime.parse(tour.getArrivalTimeDateFinish().replace(" ", "T"));

            long timeInTour = departure.until(arrival, ChronoUnit.MINUTES);

            String time = timeInTour / 60 + " hrs, " + timeInTour % 60 + " min.";

            tour.setTourTime(time);
            tour.setTourPrice((int) (timeInTour * TRIP_PRICE));

            tours.add(tour);
        }
        return tours;
    }

    @Override
    public List<Train> getAllTrains() {

        List<Train> trains = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             Statement statement = connection.createStatement()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TRAIN_DAO.getAllTrains(connection);

            while (resultSet.next()) {

                Train train = getTrainFromResultSet(resultSet);

                trains.add(train);

            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TRAINS);
        }

        return trains;
    }

    private Train getTrainFromResultSet(ResultSet resultSet) throws SQLException {

        return new TrainBuilder()
                .buildId(resultSet.getInt("train_id"))
                .buildName(resultSet.getString("name"))
                .buildEconomy(resultSet.getInt("economy"))
                .buildBusiness(resultSet.getInt("business"))
                .buildComfort(resultSet.getInt("comfort"))
                .build();

    }
}
