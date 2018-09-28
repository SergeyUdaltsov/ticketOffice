package com.service.mysqlimpl;

import com.dbConnector.MySQLConnectorManager;
import com.entity.AbstractEntity;
import com.entity.User;
import com.entity.builder.AbstractBuilder;
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
 */
public class MySQLUserService extends MySQLAbstractService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    @Override
    public void createNewUser(User user) throws SQLException {

        AbstractEntity newUser = new AbstractBuilder()
                .buildFirstName(user.getFirstName())
                .buildLastName(user.getLastName())
                .buildAdmin(user.isAdministrator())
                .buildEmail(user.getEmail())
                .buildPassword(user.getPassword())
                .buildClass(user.getClass().getSimpleName())
                .build();

        addNewItem(newUser, SQL_ADD_NEW_USER);

        LOGGER.info(USER_CREATED);
    }

    public void deleteUserById(int stationId) {


    }

    /**
     * Gets from {@code ValidateUserPasswordCommand} email and password.
     * checks the matching email to password.
     * <p>
     * if user exists, sets response status 406.
     *
     * @param email    {@code String} from {@code ValidateUserPasswordCommand} command.
     * @param password {@code String} from {@code ValidateUserPasswordCommand} command.
     */
    @Override
    public User validateUser(String email, String password) {

        Connection connection = MySQLConnectorManager.getConnection();

        MySQLConnectorManager.startTransaction(connection);

        User user = null;

        try {

            PreparedStatement statement = connection.prepareStatement(SQL_VALIDATE_PASSWORD_USER);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                user = new UserBuilder()
                        .buildId(resultSet.getInt("user_id"))
                        .buildFirstName(resultSet.getString("first_name"))
                        .buildLastName(resultSet.getString("last_name"))
                        .buildEmail(resultSet.getString("email"))
                        .buildAdmin(resultSet.getBoolean("admin"))
                        .build();

            }

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());

            MySQLConnectorManager.rollbackTransaction(connection);

        } finally {
            MySQLConnectorManager.closeConnection(connection);
        }

        return user;
    }
}
