package com.dao;


import com.service.StationService;
import com.service.UserService;
import com.service.mysqlimpl.MySQLStationService;
import com.service.mysqlimpl.MySQLUserService;

public class MySQLDAOFactory extends DAOFactory{


    @Override
    public UserService getUserService() {
        return new MySQLUserService();
    }

    @Override
    public StationService getStationService() {
        return new MySQLStationService();
    }
}
