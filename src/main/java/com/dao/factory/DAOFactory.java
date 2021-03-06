package com.dao.factory;

import com.dao.*;
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

    public abstract TicketService getTicketService(TicketDAO ticketDAO, StationService stationService, MailService mailService);

    public abstract UserService getUserService(UserDAO userDAO);

    public abstract StationService getStationService(StationDAO stationDAO, RouteDAO routeDAO);

    public abstract RouteService getRouteService(RouteDAO routeDAO, StationService stationService,
                                                 TrainDAO trainDAO, StationDAO stationDAO);

    public abstract TrainService getTrainService(TrainDAO trainDAO);

    public abstract MailService getMailService();

}
