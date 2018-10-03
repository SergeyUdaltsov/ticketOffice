package com.controller.impl.station;

import com.controller.Command;
import com.entity.Station;
import com.google.gson.Gson;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code GetIntermediateStationsByTripCommand} class is an implementation of
 * {@code Command} interface, that is responsible for retrieving list of all intermediate
 * stations according to the specified tour.
 */
public class GetIntermediateStationsByTripCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetIntermediateStationsByTripCommand.class);

    private final StationService STATION_SERVICE;

    public GetIntermediateStationsByTripCommand(StationService STATION_SERVICE) {
        this.STATION_SERVICE = STATION_SERVICE;
    }

    /**
     * Receives request and response, gets from request tour data:
     * routeId - the id of specified route, depSt - id of departure station,
     * arrSt - id of arrival station.
     *
     * Sets to the response content the list of all intermediate stations from data base
     * by these parameters.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsRouteId = request.getParameter("routeId");
        String jsDepStId = request.getParameter("depSt");
        String jsArrStId = request.getParameter("arrSt");

        int routeId = Integer.parseInt(jsRouteId);
        int depStId = Integer.parseInt(jsDepStId);
        int arrStId = Integer.parseInt(jsArrStId);

        try {

            List<Station> stations = STATION_SERVICE.getIntermediateStationsByTrip(routeId, depStId, arrStId);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            response.getWriter().write(new Gson().toJson(stations));

        } catch (SQLException | IOException e) {
            LOGGER.info(COULD_NOT_LOAD_STATIONS);
        }

        response.setStatus(200);
    }
}
