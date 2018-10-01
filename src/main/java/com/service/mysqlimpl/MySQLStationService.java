package com.service.mysqlimpl;

import com.dao.RouteDAO;
import com.dao.StationDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Station;
import com.entity.builder.StationBuilder;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.utils.UtilConstants.*;

/**
 * This is the MySQL implementation of {@code StationService interface}
 */
public class MySQLStationService extends MySQLAbstractService implements StationService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    private StationDAO stationDAO;
    private RouteDAO routeDAO;

    public MySQLStationService(StationDAO stationDAO, RouteDAO routeDAO) {
        this.stationDAO = stationDAO;
        this.routeDAO = routeDAO;
    }

    /**
     * Adds new Station to the data base.
     *
     * @param station {@code Station} from {@code AddNewStationCommand} controller.
     */
    @Override
    public void addNewStation(Station station) throws SQLException {

        stationDAO.addNewStation(station);

    }

    /**
     * Gets all the Station from data base.
     *
     * @return {@code List<Station>} all the Station stored in data base.
     */
    @Override
    public List<Station> getAllStations() {

        List<Station> stations = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = stationDAO.getAllStations(connection);

            stations = getStationsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_STATIONS);

        }
        return stations;
    }

    private List<Station> getStationsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Station> stations = new ArrayList<>();

        while (resultSet.next()) {

            Station station = new StationBuilder()
                    .buildName(resultSet.getString("name"))
                    .buildId(resultSet.getInt("station_id"))
                    .build();

            stations.add(station);
        }
        return stations;
    }

    @Override
    public Station getStationById(int stationId) {

        Station station = new Station();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_STATION_BY_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = stationDAO.getStationById(statement, stationId);

            station = getStationFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_STATION);
        }

        return station;
    }

    private Station getStationFromResultSet(ResultSet resultSet) throws SQLException {

        Station station = new Station();

        while (resultSet.next()) {

            station = new StationBuilder()
                    .buildId(resultSet.getInt("station_id"))
                    .buildName(resultSet.getString("name"))
                    .build();
        }
        return station;
    }

    @Override
    public boolean validateIntermediateStationTime(Station station) {

        LocalDateTime interArrival = LocalDateTime.of(station.getArrivalDate(), station.getArrivalTime());

        LocalDateTime interDeparture = LocalDateTime.of(station.getArrivalDate(), station.getDepartureTime());

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            ResultSet resultSet = stationDAO.getResultSetStationTimes(station, connection);

            if (Objects.nonNull(resultSet)) {

                while (resultSet.next()) {

                    LocalDateTime arrival = LocalDateTime.parse(resultSet.getString("arrival_date_time")
                            .replace(" ", "T"));

                    int stopping = resultSet.getInt("stopping");
                    LocalDateTime departure = arrival.plusMinutes(stopping);

                    if ((interArrival.isAfter(arrival) && interArrival.isBefore(departure)) ||
                             (interDeparture.isAfter(arrival) && interDeparture.isBefore(departure))) {
                        return false;
                    }

                    if ((interArrival.isBefore(arrival) && interDeparture.isAfter(departure)) ||
                            (arrival.isBefore(interArrival) && departure.isAfter(interDeparture))) {
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            LOGGER.error(COULD_NOT_RECEIVE_INTERMEDIATE_STATION_TIMES);
        }

        return true;
    }


    @Override
    public void updateStation(Station station) throws SQLException {

        stationDAO.updateStation(station);

        LOGGER.info(STATION + station.getName() + UPDATED);
    }


    @Override
    public void deleteStationById(int stationId, boolean isIntermediate) throws SQLException {

        stationDAO.deleteStationById(stationId, isIntermediate);
    }

    @Override
    public List<Station> getIntermediateStationsByTrip(int routeId, int depStId, int arrStId) throws SQLException {

        List<String> dateTimes = getDateTimeOfTrip(routeId, depStId, arrStId);

        List<Station> stations = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_STATIONS_IN_TRIP)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = stationDAO.getIntermediateStationsByTrip(statement, dateTimes, routeId);

            stations = getInterStationsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

        }

        return stations;
    }

    private List<Station> getInterStationsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Station> stations = new ArrayList<>();

        while (resultSet.next()) {

            Station station = new StationBuilder()
                    .buildName(resultSet.getString("name"))
                    .buildArrDateTimeString(resultSet.getString("arrival_date_time"))
                    .buildDepTimeString(resultSet.getString("departure_time"))
                    .build();

            stations.add(station);
        }
        return stations;
    }


    @Override
    public List<String> getDateTimeOfTrip(int routeId, int stationFrom, int stationTo) throws SQLException {

        List<String> dateTimes = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TIME_AND_DATE_OF_STATIONS_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = routeDAO.getDateTimeOfTrip(statement, routeId, stationFrom, stationTo);

            String point = "";

            while (resultSet.next()) {

                point = resultSet.getString("arrival_date_time");

                dateTimes.add(point);
            }
            MySQLConnectorManager.commitTransaction(connection);

        }

        return dateTimes;
    }

    @Override
    public Station buildIntermediateStation(int routeId, int stationId, LocalTime arrTime, LocalTime depTime,
                                            LocalDate arrDate, boolean endStation) {

        Station intermediateStation = new StationBuilder()
                .buildRouteId(routeId)
                .buildArrTime(arrTime)
                .buildDepTime(depTime)
                .buildId(stationId)
                .buildArrDate(arrDate)
                .buildEndStation(endStation)
                .build();

        return intermediateStation;


    }
}
