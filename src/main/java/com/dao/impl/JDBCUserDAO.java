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
 * The {@code JDBCUserDao} class is a JDBC implementation
 * of {@code UserDAO} interface
 */
public class JDBCUserDAO implements UserDAO {

    /**
     * Responsible for getting the ResultSet with all the data of specified User from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param email the{@code String} parameter specifies the eMail of corresponding User.
     * @param password the{@code String} parameter specifies the password of corresponding User.
     * */
    @Override
    public ResultSet validateUser(PreparedStatement statement, String email, String password) throws SQLException {

        statement.setString(1, email);
        statement.setString(2, password);

        return statement.executeQuery();
    }

    /**
     * Responsible for saving new Station to DB
     *
     * @param user the instance of {@code User} entity class
     */
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
