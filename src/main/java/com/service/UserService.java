package com.service;

import com.entity.User;
import java.sql.SQLException;

/**
 * The {@code UserService} interface is responsible for processing business logic
 * with {@code User} entity class
 */
public interface UserService {

    /**
     * Responsible for creating new User instance and save it to DB.
     *
     * @param user is {@code User} instance to save.
     */
    void createNewUser(User user) throws SQLException;

    /**
     * Responsible for validating user by checking matching email to password.
     *
     * @param email the {@code String} parameter specifies email.
     * @param password the {@code String} parameter specifies password.
     * */
    User validateUser(String email, String password);
}
