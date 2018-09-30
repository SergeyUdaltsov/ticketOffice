package com.dao;


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
    public StationService getStationService() {
        return new MySQLStationService();
    }

    @Override
    public RouteService getRouteService() {
        return new MySQLRouteService();
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
