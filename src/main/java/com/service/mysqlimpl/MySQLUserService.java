package com.service.mysqlimpl;

import com.dao.UserDAO;
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
 */
public class MySQLUserService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    private final UserDAO USER_DAO;

    public MySQLUserService(UserDAO userDAO) {
        this.USER_DAO = userDAO;
    }

    @Override
    public void createNewUser(User user) throws SQLException {

        USER_DAO.createNewUser(user);

        LOGGER.info(USER_CREATED);
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

        User user = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_VALIDATE_PASSWORD_USER)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.validateUser(statement, email, password);

            user = getUserFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(USER_NOT_VALIDATED);

        }

        return user;
    }

    /**
     * Responsible for creatinf {@code User} instance from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code validateUser()} method.
     * @return {@code User} instance.
     * */
    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {

        User user = null;

        while (resultSet.next()) {

            user = new UserBuilder()
                    .buildId(resultSet.getInt("user_id"))
                    .buildFirstName(resultSet.getString("first_name"))
                    .buildLastName(resultSet.getString("last_name"))
                    .buildEmail(resultSet.getString("email"))
                    .buildAdmin(resultSet.getBoolean("admin"))
                    .buildPassword(resultSet.getString("pswrd"))
                    .build();
        }
        return user;
    }
}
