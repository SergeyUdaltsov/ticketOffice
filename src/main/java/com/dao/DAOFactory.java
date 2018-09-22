package com.dao;

import com.service.StationService;
import com.service.UserService;

import static com.utils.UtilConstants.*;

public abstract class DAOFactory {

    private static final String DATA_BASE = MYSQL_DATA_BASE;

    public static DAOFactory getDAOFactory() {
        switch (DATA_BASE) {
            case "MySQL": {

                return new MySQLDAOFactory();
            }
            default:
                return null;
        }
    }

    public abstract UserService getUserService();
    public abstract StationService getStationService();

}
