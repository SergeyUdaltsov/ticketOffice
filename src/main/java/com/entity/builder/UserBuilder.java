package com.entity.builder;

import com.entity.User;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder buildId(int id) {
        user.setId(id);
        return this;
    }

    public UserBuilder buildFirstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder buildLastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder buildEmail(String email) {
        user.setEmail(email);;
        return this;
    }

    public UserBuilder buildPassword(String password) {
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder buildAdmin(boolean admin) {
        this.user.setAdministrator(admin);
        return this;
    }

    public User build() {
        return user;
    }

}
