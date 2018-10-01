package com.dao.factory;


import com.dao.RouteDAO;
import com.dao.StationDAO;
import com.service.*;
import com.service.mysqlimpl.*;

public class MySQLDAOFactory extends DAOFactory{


    @Override
    public TicketService getTicketService() {
        return new MySQLTicketService();
    }

    @Override
    public UserService getUserService() {
        return new MySQLUserService();
    }

    @Override
    public StationService getStationService(StationDAO stationDAO) {
        return new MySQLStationService(stationDAO);
    }

    @Override
    public RouteService getRouteService(RouteDAO routeDAO, StationService stationService) {
        return new MySQLRouteService(routeDAO, stationService);
    }

    @Override
    public TrainService getTrainService() {
        return  new MySQLTrainService();
    }

    @Override
    public MailService getMailService() {
        return new MySQLMailService();
    }
}
