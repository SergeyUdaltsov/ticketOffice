package com.controller.impl.route;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.dao.impl.JDBCRouteDAO;
import com.entity.Station;
import com.google.gson.Gson;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.utils.UtilConstants.CONTENT_TYPE;
import static com.utils.UtilConstants.COULD_NOT_LOAD_ROUTES;
import static com.utils.UtilConstants.ENCODING;

/**
 * The {@code GetIntermediateStationsByRouteCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting list of intermediate stations
 * by specified route.
 */
public class GetIntermediateStationsByRouteCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetIntermediateStationsByRouteCommand.class);

    private final RouteService ROUTE_SERVICE;

    public GetIntermediateStationsByRouteCommand(RouteService ROUTE_SERVICE) {
        this.ROUTE_SERVICE = ROUTE_SERVICE;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String objStr = request.getParameter("routeId");

        int stationId = Integer.parseInt(objStr);

        List<Station> stations = ROUTE_SERVICE.getIntermediateStationsByRouteId(stationId);

        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);

        try {

            response.getWriter().write(new Gson().toJson(stations));

        } catch (IOException e) {
            LOGGER.error(COULD_NOT_LOAD_ROUTES);
        }
    }
}
