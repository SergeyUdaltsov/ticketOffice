package com.dao.factory;


import com.dao.RouteDAO;
import com.dao.StationDAO;
import com.dao.TicketDAO;
import com.dao.TrainDAO;
import com.service.*;
import com.service.mysqlimpl.*;

public class MySQLDAOFactory extends DAOFactory{


    @Override
    public TicketService getTicketService(TicketDAO ticketDAO, StationService stationService, MailService mailService) {
        return new MySQLTicketService(ticketDAO, stationService, mailService);
    }

    @Override
    public UserService getUserService() {
        return new MySQLUserService();
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
    public TrainService getTrainService() {
        return  new MySQLTrainService();
    }

    @Override
    public MailService getMailService() {
        return new MySQLMailService();
    }
}
