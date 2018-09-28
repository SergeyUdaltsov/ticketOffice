package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.AbstractEntity;
import com.entity.Train;
import com.entity.builder.AbstractBuilder;
import com.entity.builder.TrainBuilder;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * This is the MySQL implementation of {@code TrainService interface}
 */
public class MySQLTrainService extends MySQLAbstractService implements TrainService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLTrainService.class);


    @Override
    public void addNewTrain(Train train) throws SQLException {

        AbstractEntity newTrain = new AbstractBuilder()
                .buildStringField1(train.getName())
                .buildIntField1(train.getEconomyPlacesCount())
                .buildIntField2(train.getBusinessPlacesCount())
                .buildIntField3(train.getComfortPlacesCount())
                .buildClass(train.getClass().getSimpleName())
                .build();

        addNewItem(newTrain, SQL_ADD_NEW_TRAIN);

    }


    @Override
    public Train getTrainById(int trainId) {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        Train train = null;

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_TRAIN_BY_ID);

            statement.setInt(1, trainId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                train = populateTrain(resultSet);
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return train;
    }

    @Override
    public void updateTrain(Train train) throws SQLException {

        AbstractEntity trainToUpdate = new AbstractBuilder()
                .buildIntField1(train.getId())
                .buildStringField1(train.getName())
                .buildIntField2(train.getEconomyPlacesCount())
                .buildIntField3(train.getBusinessPlacesCount())
                .buildIntField4(train.getComfortPlacesCount())
                .buildClass(train.getClass().getSimpleName())
                .build();

        updateItem(trainToUpdate, SQL_UPDATE_TRAIN);
    }

    @Override
    public void deleteTrainById(int trainId) throws SQLException {
        deleteItem(trainId, SQL_DELETE_TRAIN_BY_ID);
    }

    @Override
    public List<AbstractEntity> getTrainsByStations(int departureStationId, int arrivalStationId) {

        List<AbstractEntity> tours = new ArrayList<>();

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SHOW_TRAINS_BY_STATIONS);

            statement.setInt(1, departureStationId);
            statement.setInt(2, arrivalStationId);
            statement.setInt(3, departureStationId);
            statement.setInt(4, arrivalStationId);
            statement.setInt(5, departureStationId);
            statement.setInt(6, arrivalStationId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AbstractEntity tour = new AbstractBuilder()
                        .buildCode(resultSet.getString("train"))
                        .buildRouteId(resultSet.getInt("route_id"))
                        .buildDepDate(LocalDate.parse(resultSet.getString("dep_date")))
                        .buildDepTime(LocalTime.parse(resultSet.getString("departure_time")))
                        .buildStartStationId(resultSet.getInt("dep_st_id"))
                        .buildFinishStationId(resultSet.getInt("arr_st_id"))
                        .buildDepStation(resultSet.getString("dep_st"))
                        .buildArrDate(LocalDate.parse(resultSet.getString("arr_date")))
                        .buildArrTime(LocalTime.parse(resultSet.getString("arrival_time")))
                        .buildArrStation(resultSet.getString("arr_st"))
                        .build();

                LocalDateTime departure = LocalDateTime.of(tour.getDepartureDate(), tour.getDepartureTime());
                LocalDateTime arrival = LocalDateTime.of(tour.getArrivalDate(), tour.getArrivalTime());

                long timeInTour = departure.until(arrival, ChronoUnit.MINUTES);

                String time = timeInTour / 60 + " hrs, " + timeInTour % 60 + " min.";

                tour.setTourTime(time);
                tour.setTourPrice((int)(timeInTour / 2));

                tours.add(tour);
            }

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return tours;
    }

    @Override
    public List<Train> getAllTrains() {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        List<Train> trains = new ArrayList<>();

        try (Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_TRAINS)) {

            while (resultSet.next()) {

                Train train = populateTrain(resultSet);

                trains.add(train);
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }


        return trains;
    }

    private Train populateTrain(ResultSet resultSet) throws SQLException {

        Train train = new TrainBuilder()
                .buildId(resultSet.getInt("train_id"))
                .buildName(resultSet.getString("name"))
                .buildEconomy(resultSet.getInt("economy"))
                .buildBusiness(resultSet.getInt("business"))
                .buildComfort(resultSet.getInt("comfort"))
                .build();
        return train;
    }
}
