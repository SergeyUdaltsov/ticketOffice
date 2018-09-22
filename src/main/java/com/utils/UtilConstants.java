package com.utils;

public class UtilConstants {

    //command url patterns:
    public static final String FRONT_CONTROLLER_SERVLET = "/railways";
    public static final String FRONT_CONTROLLER_SERVLET_ALL_VARIATION = "/railways/*";

    //command urls:
    public static final String REGISTER_NEW_USER_COMMAND = "/user/register";
    public static final String VALIDATE_USER_PASSWORD_COMMAND = "/user/validate";

    public static final String ADD_NEW_STATION_COMMAND = "/station/new";

    //db constants:
    public static final String MYSQL_DATA_BASE = "MySQL";


    //train:
    public static final int BUSINESS_PLACES_COUNT = 100;
    public static final int COMFORT_PLACES_COUNT = 50;
    public static final int ECONOMY_PLACES_COUNT = 150;


    //SQL User
    public static final String SQL_CHECK_IF_EXISTS_USER = "SELECT COUNT(user_id) FROM users WHERE email=(?);";
    public static final String SQL_ADD_NEW_USER = "INSERT INTO users(first_name, last_name, email, pswrd, admin)" +
            " VALUES(?, ?, ?, ?, ?);";
    public static final String SQL_VALIDATE_PASSWORD_USER = "SELECT * FROM users WHERE email=(?) AND pswrd=(?);";

    //SQL Station
    public static final String SQL_CHECK_IF_EXISTS_STATION = "SELECT COUNT(station_id) FROM station WHERE name=(?);";
    public static final String SQL_ADD_NEW_STATION = "INSERT INTO station(name) VALUES(?)";
    public static final String SQL_GET_ALL_STATIONS = "SELECT * FROM station";
}
