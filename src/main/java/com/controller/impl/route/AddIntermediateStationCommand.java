package com.controller.impl.route;

import com.controller.Command;
import com.entity.Station;
import com.service.RouteService;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.utils.UtilConstants.*;

/**
 * The {@code AddIntermediateStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new route.
 */
public class AddIntermediateStationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddIntermediateStationCommand.class);

    private final RouteService ROUTE_SERVICE;
    private final StationService STATION_SERVICE;


    public AddIntermediateStationCommand(RouteService ROUTE_SERVICE, StationService STATION_SERVICE) {
        this.ROUTE_SERVICE = ROUTE_SERVICE;
        this.STATION_SERVICE = STATION_SERVICE;
    }

    /**
     * Receives request and response gets route from request,
     * add new intermediate station to the specified route.
     * <p>
     * if station exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int status = 200;

        try {

            Station station = getStationFromRequest(request);

            ROUTE_SERVICE.addIntermediateStation(station);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
            status = 406;
        }

        response.setStatus(status);
    }


    private Station getStationFromRequest(HttpServletRequest request) {

        String jsStr = request.getParameter("jsonStation");

        Station station = null;

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            int interStationId = jsonObject.getInt("id");
            int routeId = jsonObject.getInt("routeId");
            LocalTime departure = LocalTime.parse(jsonObject.getString("depTime"));
            LocalTime arrival = LocalTime.parse(jsonObject.getString("arrTime"));
            LocalDate arrDate = LocalDate.parse(jsonObject.getString("arrDate"));

            station = STATION_SERVICE.buildIntermediateStation(routeId, interStationId,
                    arrival, departure, arrDate, false);

        } catch (JSONException e) {
            LOGGER.error(WRONG_DATA_FROM_CLIENT_STATION);
        }

        return station;
    }

}
