package com.dao.impl;

import com.dao.CommonsOperable;
import com.dao.TrainDAO;
import com.dbConnector.MySQLConnectorManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 01.10.2018.
 */
public class JDBCTrainDAO implements TrainDAO, CommonsOperable {

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
}
