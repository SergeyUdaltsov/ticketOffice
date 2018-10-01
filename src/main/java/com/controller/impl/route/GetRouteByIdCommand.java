package com.controller.impl.route;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.entity.Route;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import static com.utils.UtilData.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Serg on 27.09.2018.
 */
public class GetRouteByIdCommand implements Command {

    private final RouteService SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(GetRouteByIdCommand.class);

    public GetRouteByIdCommand(RouteService SERVICE) {
        this.SERVICE = SERVICE;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String objStr = request.getParameter("routeId");

        int routeId = Integer.parseInt(objStr);

        Route route = SERVICE.getRouteById(routeId);

        try {

            sendJson(route, response);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
