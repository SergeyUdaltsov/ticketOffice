package com.dao;

import com.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code UserDao} interface is responsible for
 * connecting {@code User} entity class to DB
 */
public interface UserDAO {

    /**
     * Responsible for saving new Station to DB
     *
     * @param user the instance of {@code User} entity class
     */
    void createNewUser(User user) throws SQLException;

    /**
     * Responsible for getting the ResultSet with all the data of specified User from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param email the{@code String} parameter specifies the eMail of corresponding User.
     * @param password the{@code String} parameter specifies the password of corresponding User.
     * */
    ResultSet validateUser(PreparedStatement statement, String email, String password) throws SQLException;
}
