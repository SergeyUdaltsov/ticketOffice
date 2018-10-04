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

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
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

    /**
     * Responsible for creating new Train instance and save it to DB.
     *
     * @param train is {@code Train} instance to save.
     */
    @Override
    public void addNewTrain(Train train) throws SQLException {

        TRAIN_DAO.addNewTrain(train);
    }


    /**
     * Responsible for getting Train instance with specified id.
     *
     * @param trainId the {@code int} parameter specifies Train.
     * @return {@code Train} instance
     */
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

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TRAINS);

        }
        return train;
    }

    /**
     * Responsible for updating train with specified id with new values.
     *
     * @param train the {@code Train} instance encapsulating new values.
     */
    @Override
    public void updateTrain(Train train) throws SQLException {

        TRAIN_DAO.updateTrain(train);
    }

    /**
     * Responsible for deleting specified Train from DB.
     *
     * @param trainId the {@code int} parameter specifies Train.
     */
    @Override
    public void deleteTrainById(int trainId) throws SQLException {
        TRAIN_DAO.deleteTrainById(trainId);
    }

    /**
     * Responsible for getting the list of all trains
     * which go through between specified stations.
     *
     * @param departureStationId the {@code int} parameter specifies departure station.
     * @param arrivalStationId   the {@code int} parameter specifies arrival station.
     * @return {@code List<Tour>} list of tours between specified stations.
     */
    @Override
    public List<Tour> getTrainsByStations(int departureStationId, int arrivalStationId, LocalDate depDate) {

        List<Tour> tours = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SHOW_TRAINS_BY_STATIONS)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TRAIN_DAO.getTrainsByStations(statement, departureStationId, arrivalStationId, depDate);

            tours = getToursFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_ROUTES);
        }
        return tours;
    }

    /**
     * responsible for creating {@code List<Tour>} from ResultSet.
     *
     * @param resultSet the {@code ResultSet} instance from {@code getTrainsByStations()} method.
     * @return {@code List<Tour>} of Tour.
     * */
    private List<Tour> getToursFromResultSet(ResultSet resultSet) throws SQLException {

        List<Tour> tours = new ArrayList<>();

        while (resultSet.next()) {

            Tour tour = new TourBuilder()
                    .buildTrain(resultSet.getString("train"))
                    .buildRouteId(resultSet.getInt("route_id"))
                    .buildArrivalDateTimeStart(resultSet.getString("arr_date_from"))
                    .buildDepartureTime(resultSet.getString("dep_time"))
                    .buildDepartureStation(resultSet.getString("dep_st"))
                    .buildDepartureStationRu(resultSet.getString("dep_st_ru"))
                    .buildDepartureStationId(resultSet.getInt("dep_st_id"))
                    .buildArrivalTimeDateFinish(resultSet.getString("arr_date_to"))
                    .buildArrivalStation(resultSet.getString("arr_st"))
                    .buildArrivalStationRu(resultSet.getString("arr_st_ru"))
                    .buildArrivalStationId(resultSet.getInt("arr_st_id"))
                    .build();

            LocalDateTime departure = LocalDateTime.parse(tour.getArrivalTimeDateStart().replace(" ", "T"));
            LocalDateTime arrival = LocalDateTime.parse(tour.getArrivalTimeDateFinish().replace(" ", "T"));

            long timeInTour = departure.until(arrival, ChronoUnit.MINUTES);

            String time = timeInTour / 60 + " hrs, " + timeInTour % 60 + " min.";

            tour.setTourTime(time);
            tour.setTourPriceEco(BigDecimal.valueOf(timeInTour * TRIP_PRICE_ECO)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            tour.setTourPriceBusiness(BigDecimal.valueOf(timeInTour * TRIP_PRICE_BUS)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            tour.setTourPriceComfort(BigDecimal.valueOf(timeInTour * TRIP_PRICE_COM)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            tours.add(tour);
        }
        return tours;
    }

    /**
     * Responsible for getting the list of all Train from DB.
     *
     * @return {@code List<Train>} the list of all Train from DB.
     */
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

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TRAINS);
        }

        return trains;
    }

    /**
     * responsible for creating {@code List<Train>} from ResultSet.
     *
     * @param resultSet the {@code ResultSet} instance from {@code getAllTrains()} method.
     * @return {@code List<Train>} of Train.
     * */
    private Train getTrainFromResultSet(ResultSet resultSet) throws SQLException {

        return new TrainBuilder()
                .buildId(resultSet.getInt("train_id"))
                .buildName(resultSet.getString("name"))
                .buildNameRu(resultSet.getString("name_ru"))
                .buildEconomy(resultSet.getInt("economy"))
                .buildBusiness(resultSet.getInt("business"))
                .buildComfort(resultSet.getInt("comfort"))
                .build();

    }
}
