package com.controller.impl.route;

import com.controller.Command;
import com.dao.DAOFactory;
import com.entity.Route;
import com.entity.builder.RouteBuilder;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@code AddNewRouteCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new route.
 */
public class AddNewRouteCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddNewRouteCommand.class);

    private static final RouteService SERVICE = DAOFactory.getDAOFactory().getRouteService();


    /**
     * Receives request and response gets route from request,
     * checks route for existing and perists new route to data base.
     * <p>
     * if route exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonRoute");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);
            String code = jsonObject.getString("code");
            int stationStartId = jsonObject.getInt("stStart");
            LocalTime departure = LocalTime.parse(jsonObject.getString("departureTime"));
            int stationFinishId = jsonObject.getInt("stFinish");
            LocalTime arrival = LocalTime.parse(jsonObject.getString("arrivalTime"));
            LocalDate arrDate = LocalDate.parse(jsonObject.getString("arrivalDate"));
            LocalDate depDate = LocalDate.parse(jsonObject.getString("departureDate"));


            Route route = new RouteBuilder()
                    .buildCode(code)
                    .buildStartStation(stationStartId)
                    .buildFinishStation(stationFinishId)
                    .buildDepartureTime(departure)
                    .buildArrivalTime(arrival)
                    .buildArrivalDate(arrDate)
                    .buildDepartureDate(depDate)
                    .build();

            try {

                SERVICE.addNewRoute(route);

            } catch (SQLException e) {

                LOGGER.error(e.getMessage());
                response.setStatus(406);
                return;
            }

        } catch (JSONException e) {
            LOGGER.error(e.getMessage());
        }

        response.setStatus(200);

    }
}
