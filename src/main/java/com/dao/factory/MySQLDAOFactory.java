package com.dao.factory;


import com.dao.*;
import com.service.*;
import com.service.mysqlimpl.*;

public class MySQLDAOFactory extends DAOFactory {

    /**
     * Method responsible for getting the instance of service class MySQLTicketService implementation.
     *
     * @param ticketDAO      {@code TicketDAO} instance
     * @param stationService {@code StationService} instance
     * @param mailService    {@code MailService} instance
     */
    @Override
    public TicketService getTicketService(TicketDAO ticketDAO, StationService stationService, MailService mailService) {
        return new MySQLTicketService(ticketDAO, stationService, mailService);
    }

    /**
     * Method responsible for getting the instance of service class MySQLUserService implementation.
     *
     * @param userDAO {@code UserDAO} instance
     * */
    @Override
    public UserService getUserService(UserDAO userDAO) {
        return new MySQLUserService(userDAO);
    }

    /**
     * Method responsible for getting the instance of service class MySQLStationService implementation.
     *
     * @param stationDAO {@code StationDAO} instance
     * @param routeDAO {@code RouteDAO} instance
     * */
    @Override
    public StationService getStationService(StationDAO stationDAO, RouteDAO routeDAO) {
        return new MySQLStationService(stationDAO, routeDAO);
    }

    /**
     * Method responsible for getting the instance of service class MySQLSRouteService implementation.
     *
     * @param stationDAO {@code StationDAO} instance
     * @param routeDAO {@code RouteDAO} instance
     * @param trainDAO {@code TrainDAO} instance
     * @param stationService {@code StationDAO} instance
     * */
    @Override
    public RouteService getRouteService(RouteDAO routeDAO, StationService stationService,
                                        TrainDAO trainDAO, StationDAO stationDAO) {
        return new MySQLRouteService(routeDAO, trainDAO, stationDAO, stationService);
    }

    /**
     * Method responsible for getting the instance of service class MySQLSTrainService implementation.
     *
     * @param trainDAO {@code TrainDAO} instance
     * */
    @Override
    public TrainService getTrainService(TrainDAO trainDAO) {
        return new MySQLTrainService(trainDAO);
    }


    /**
     * Method responsible for getting the instance of service class MySQLSMailService implementation.
     *
     * */
    @Override
    public MailService getMailService() {
        return new MySQLMailService();
    }
}
