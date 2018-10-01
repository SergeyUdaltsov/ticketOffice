package com.dao.factory;


import com.dao.*;
import com.service.*;
import com.service.mysqlimpl.*;

public class MySQLDAOFactory extends DAOFactory{


    @Override
    public TicketService getTicketService(TicketDAO ticketDAO, StationService stationService, MailService mailService) {
        return new MySQLTicketService(ticketDAO, stationService, mailService);
    }

    @Override
    public UserService getUserService(UserDAO userDAO) {
        return new MySQLUserService(userDAO);
    }

    @Override
    public StationService getStationService(StationDAO stationDAO, RouteDAO routeDAO) {
        return new MySQLStationService(stationDAO, routeDAO);
    }

    @Override
    public RouteService getRouteService(RouteDAO routeDAO, StationService stationService, TrainDAO trainDAO, StationDAO stationDAO) {
        return new MySQLRouteService(routeDAO, trainDAO, stationDAO, stationService);
    }

    @Override
    public TrainService getTrainService(TrainDAO trainDAO) {
        return  new MySQLTrainService(trainDAO);
    }

    @Override
    public MailService getMailService() {
        return new MySQLMailService();
    }
}
