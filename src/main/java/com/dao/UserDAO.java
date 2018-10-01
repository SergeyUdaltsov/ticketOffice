package com.dao;

import com.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Serg on 02.10.2018.
 */
public interface UserDAO {

    void createNewUser(User user) throws SQLException;

    ResultSet validateUser(PreparedStatement statement, String email, String password) throws SQLException;
}
