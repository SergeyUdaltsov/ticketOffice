package com.utils;

import com.controller.impl.route.AddIntermediateStationCommand;
import com.controller.impl.route.DeleteIntermediateStationByIdCommand;
import com.controller.impl.route.*;
import com.controller.impl.station.*;
import com.controller.impl.ticket.BuyTicketsCommand;
import com.controller.impl.station.GetIntermediateStationsByTripCommand;
import com.controller.impl.ticket.GetTicketsCountCommand;
import com.controller.impl.train.*;
import com.controller.impl.user.RegisterNewUserCommand;
import com.controller.Command;
import com.controller.impl.user.ValidateUserPasswordCommand;
import com.dao.factory.DAOFactory;
import com.dao.RouteDAO;
import com.dao.impl.JDBCRouteDAO;
import com.dao.impl.JDBCStationDAO;
import com.dao.StationDAO;
import com.entity.Sendable;
import com.google.gson.Gson;
import com.service.RouteService;
import com.service.StationService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.utils.UtilConstants.*;

/**
 * The {@code UtilData} class is responsible for holding all service collections including application data
 */
public class UtilData {

    public static final Map<String, Command> COMMANDS_MAP = new HashMap<>();




    static {

        RouteDAO routeDAO = new JDBCRouteDAO();
        StationDAO stationDAO = new JDBCStationDAO();

        StationService stationService = DAOFactory.getDAOFactory().getStationService(stationDAO);
        RouteService routeService = DAOFactory.getDAOFactory().getRouteService(routeDAO, stationService);


        COMMANDS_MAP.put(REGISTER_NEW_USER_COMMAND, new RegisterNewUserCommand());
        COMMANDS_MAP.put(VALIDATE_USER_PASSWORD_COMMAND, new ValidateUserPasswordCommand());
        COMMANDS_MAP.put(ADD_NEW_STATION_COMMAND, new AddNewStationCommand(stationService));
        COMMANDS_MAP.put(GET_ALL_STATIONS_COMMAND, new GetAllStationsCommand(stationService));
        COMMANDS_MAP.put(GET_STATION_BY_ID_COMMAND, new GetStationByIdCommand(stationService));
        COMMANDS_MAP.put(UPDATE_STATION_COMMAND, new UpdateStationCommand(stationService));
        COMMANDS_MAP.put(DELETE_STATION_COMMAND, new DeleteStationCommand(stationService));
        COMMANDS_MAP.put(ADD_NEW_ROUTE_COMMAND, new AddNewRouteCommand(routeService));
        COMMANDS_MAP.put(GET_ALL_ROUTES_COMMAND, new GetAllRoutesCommand(routeService));
        COMMANDS_MAP.put(GET_ROUTE_BY_ID_COMMAND, new GetRouteByIdCommand(routeService));
        COMMANDS_MAP.put(ADD_INTERMEDIATE_STATION_COMMAND, new AddIntermediateStationCommand(routeService, stationService));
        COMMANDS_MAP.put(GET_INTERMEDIATE_STATIONS_BY_ROUTE, new GetIntermediateStationsByRouteCommand(routeService));
        COMMANDS_MAP.put(DELETE_INTERMEDIATE_STATION_BY_ID_COMMAND, new DeleteIntermediateStationByIdCommand(stationService));
        COMMANDS_MAP.put(DELETE_ROUTE_BY_ID_COMMAND, new DeleteRouteByIdCommand(routeService));
        COMMANDS_MAP.put(ADD_NEW_TRAIN_COMMAND, new AddNewTrainCommand());
        COMMANDS_MAP.put(GET_ALL_TRAINS_COMMAND, new GetAllTrainsCommand());
        COMMANDS_MAP.put(GET_TRAIN_BY_ID_COMMAND, new GetTrainByIdCommand());
        COMMANDS_MAP.put(UPDATE_TRAIN_COMMAND, new UpdateTrainCommand());
        COMMANDS_MAP.put(DELETE_TRAIN_COMMAND, new DeleteTrainByIdCommand());
        COMMANDS_MAP.put(SET_TRAIN_COMMAND, new SetTrainToRouteCommand(routeService));
        COMMANDS_MAP.put(SHOW_TRAINS_COMMAND, new ShowTrainsCommand());
        COMMANDS_MAP.put(GET_TICKETS_COUNT_COMMAND, new GetTicketsCountCommand());
        COMMANDS_MAP.put(GET_STATIONS_BY_TRIP_COMMAND, new GetIntermediateStationsByTripCommand(stationService));
        COMMANDS_MAP.put(BUY_TICKETS_COMMAND, new BuyTicketsCommand());

    }

    public static void sendJson(Sendable entity, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(new Gson().toJson(entity));
    }

}
