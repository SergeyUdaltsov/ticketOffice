package com.dao;

import com.service.*;

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

    public abstract TicketService getTicketService();

    public abstract UserService getUserService();

    public abstract StationService getStationService();

    public abstract RouteService getRouteService();

    public abstract TrainService getTrainService();

    public abstract MailService getMailService();

}
