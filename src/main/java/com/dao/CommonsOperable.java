package com.dao;

import com.dbConnector.MySQLConnectorManager;
import com.entity.Station;

import java.sql.*;


/**
 * Created by Serg on 01.10.2018.
 */
public interface CommonsOperable {


    default void deleteItemById(int itemId, String query) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            MySQLConnectorManager.startTransaction(connection);


            statement.setInt(1, itemId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }
    }

    default ResultSet getAllItems(Connection connection, String query) throws SQLException {

        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }


    default ResultSet getItemById(Connection connection, String query, int itemId) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, itemId);

        return statement.executeQuery();
    }
}
