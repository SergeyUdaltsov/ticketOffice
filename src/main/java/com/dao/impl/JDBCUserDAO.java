package com.dao.impl;

import com.dao.UserDAO;
import com.dbConnector.MySQLConnectorManager;
import com.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 02.10.2018.
 */
public class JDBCUserDAO implements UserDAO {


    @Override
    public ResultSet validateUser(PreparedStatement statement, String email, String password) throws SQLException {

        statement.setString(1, email);
        statement.setString(2, password);

        return statement.executeQuery();
    }

    @Override
    public void createNewUser(User user) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_USER)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, false);
            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(USER_EXISTS);
        }
    }
}
