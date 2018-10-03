package com.controller.impl.route;

import com.controller.Command;
import com.entity.Route;
import com.google.gson.Gson;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.utils.UtilConstants.CONTENT_TYPE;
import static com.utils.UtilConstants.ENCODING;

/**
 * The {@code GetRouteByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible for retrieving route
 * data with the specified route id.
 */
public class GetRouteByIdCommand implements Command {

    private final RouteService SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(GetRouteByIdCommand.class);

    public GetRouteByIdCommand(RouteService SERVICE) {
        this.SERVICE = SERVICE;
    }


    /**
     * Receives request and response, gets route id from request.
     * Sets to the response content route data with the specified route id.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String objStr = request.getParameter("routeId");

        int routeId = Integer.parseInt(objStr);

        Route route = SERVICE.getRouteById(routeId);

        try {

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().write(new Gson().toJson(route));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
