package com.service;


import com.entity.User;

public interface UserService {

    public abstract void createNewUser(User user);
    public abstract boolean checkIfUserExists(String email);
    public abstract User validateUser(String email, String password);
}
