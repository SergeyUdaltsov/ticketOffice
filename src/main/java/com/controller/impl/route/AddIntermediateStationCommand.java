package com.controller.impl.route;

import com.controller.Command;
import com.dao.DAOFactory;
import com.entity.AbstractEntity;
import com.entity.builder.AbstractBuilder;
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

import static com.utils.UtilConstants.*;

/**
 * The {@code AddIntermediateStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new route.
 */
public class AddIntermediateStationCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger(AddIntermediateStationCommand.class);

    private static final RouteService SERVICE = DAOFactory.getDAOFactory().getRouteService();


    /**
     * Receives request and response gets route from request,
     * add new intermediate station to the specified route.
     *
     * if station exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonStation");

        int status = 200;

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            int interStationId = jsonObject.getInt("id");
            int routeId = jsonObject.getInt("routeId");
            LocalTime departure = LocalTime.parse(jsonObject.getString("depTime"));
            LocalTime arrival = LocalTime.parse(jsonObject.getString("arrTime"));
            LocalDate arrDate = LocalDate.parse(jsonObject.getString("arrDate"));

            AbstractEntity interStation = new AbstractBuilder()
                    .buildRouteId(routeId)
                    .buildArrTime(arrival)
                    .buildDepTime(departure)
                    .buildStartStationId(interStationId)
                    .buildClass(INTER_STATION)
                    .buildArrDate(arrDate)
                    .build();

            try {

                SERVICE.addIntermediateStation(interStation);

            } catch (SQLException e) {

                LOGGER.error(e.getMessage());
                status = 406;
            }

        } catch (JSONException e) {
            LOGGER.error(e.getMessage());
        }

        response.setStatus(status);

    }
}
