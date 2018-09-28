package com.service;


import com.entity.User;

import java.sql.SQLException;

public interface UserService {

    public abstract void createNewUser(User user) throws SQLException;
    public abstract User validateUser(String email, String password);
}
