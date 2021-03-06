package com.controller.impl.route;

import com.controller.Command;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Serg on 25.09.2018.
 */
public class SetTrainToRouteCommand implements Command {

    private final RouteService SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(SetTrainToRouteCommand.class);

    public SetTrainToRouteCommand(RouteService SERVICE) {
        this.SERVICE = SERVICE;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String trainIdStr = request.getParameter("trainId");
        String routeIdStr = request.getParameter("routeId");

        int trainId = Integer.parseInt(trainIdStr);
        int routeId = Integer.parseInt(routeIdStr);

        SERVICE.setTrain(trainId, routeId);
    }
}
