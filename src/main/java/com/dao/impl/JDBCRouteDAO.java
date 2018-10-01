package com.dao.impl;

import com.dao.CommonsOperable;
import com.dao.RouteDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.Route;
import com.entity.Station;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 30.09.2018.
 */
public class JDBCRouteDAO implements RouteDAO, CommonsOperable {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.LogManager.getLogger(JDBCRouteDAO.class);


    @Override
    public int addNewRoute(Route route) throws SQLException {

        int routeId = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_ROUTE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, route.getCode());
            statement.setInt(2, route.getStartStationId());
            statement.setString(3, route.getDepartureTime().toString());
            statement.setString(4, route.getDepartureDate().toString());
            statement.setString(5, route.getArrivalDate().toString());
            statement.setInt(6, route.getFinishStationId());
            statement.setString(7, route.getArrivalTime().toString());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                routeId = rs.getInt(1);
            }

            LOGGER.info(ROUTE + CREATED + FROM + route.getStartStationId() + TO + route.getFinishStationId());

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_ROUTE);
        }

        return routeId;
    }

    @Override
    public void addIntermediateStation(Station station) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_INTERMEDIATE_STATION)) {

            LocalDateTime arrDateTime = LocalDateTime.of(station.getArrivalDate(), station.getArrivalTime());
            LocalDateTime depDateTime = station.getDepartureTime().atDate(station.getArrivalDate());

            int stopping = (int) arrDateTime.until(depDateTime, ChronoUnit.MINUTES);

            statement.setInt(1, station.getRouteId());
            statement.setInt(2, station.getId());
            statement.setString(3, arrDateTime.toString());
            statement.setInt(4, stopping);
            statement.setString(5, station.getDepartureTime().toString());

            statement.executeUpdate();

            LOGGER.info(INTERMEDIATE_STATION_ADDED);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_STATION);
        }

    }

    @Override
    public void deleteRouteById(int routeId) throws SQLException {

        deleteItemById(routeId, SQL_DELETE_ROUTE_BY_ID);

        LOGGER.info(ROUTE + routeId + DELETED);
    }

    @Override
    public ResultSet getAllRoutes(Connection connection) throws SQLException {

        String query = SQL_GET_ALL_ROUTES;

        return getAllItems(connection, query);
    }

    @Override
    public ResultSet getIntermediateStationsByRouteId(Connection connection, int routeId) throws SQLException {

        PreparedStatement statement =connection.prepareStatement(SQL_GET_INTERMEDIATE_STATIONS_BY_ROUTE);

        statement.setInt(1, routeId);

        return statement.executeQuery();
    }

    @Override
    public ResultSet getRouteById(int routeId, Connection connection) throws SQLException {

        String query = SQL_GET_ROUTE_BY_ID;

        return getItemById(connection, query, routeId);

    }
}
