package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.User;
import com.entity.builder.UserBuilder;
import com.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * This is the MySQL implementation of {@code UserService interface}
 *
 */
public class MySQLUserService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    @Override
    public void createNewUser(User user) {
        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_USER);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, false);
            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }
        LOGGER.info("User created");
    }

    @Override
    public boolean checkIfUserExists(String email) {
        boolean exists = false;

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_CHECK_IF_EXISTS_USER);

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int count = resultSet.getInt(1);

                if (count != 0) {
                    exists = true;
                }
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return exists;
    }

    /**
     * Gets from {@code ValidateUserPasswordCommand} email and password.
     * checks the matching email to password.
     *
     * if user exists, sets response status 406.
     *
     * @param email {@code String} from {@code ValidateUserPasswordCommand} command.
     * @param password {@code String} from {@code ValidateUserPasswordCommand} command.
     */
    @Override
    public User validateUser(String email, String password) {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_VALIDATE_PASSWORD_USER);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                User user = new UserBuilder()
                        .buildId(resultSet.getInt("user_id"))
                        .buildFirstName(resultSet.getString("first_name"))
                        .buildLastName(resultSet.getString("last_name"))
                        .buildEmail(resultSet.getString("email"))
                        .buildAdmin(resultSet.getBoolean("admin"))
                        .build();

                return user;
            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return null;
    }
}
