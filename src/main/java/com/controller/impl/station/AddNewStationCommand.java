package com.controller.impl.station;

import com.controller.Command;
import com.dao.DAOFactory;
import com.entity.Station;
import com.entity.builder.StationBuilder;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * The {@code AddNewStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new stations.
 */
public class AddNewStationCommand implements Command {


    private static final Logger LOGGER = LogManager.getLogger(AddNewStationCommand.class);

    private static final StationService SERVICE = DAOFactory.getDAOFactory().getStationService();


    /**
     * Receives request and response, gets station from request,
     * checks station for existing using and creates new station.
     *
     * if station exists, sets response status 406.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonStation");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);
            String name = jsonObject.getString("name");

            Station station = new StationBuilder()
                    .buildName(name)
                    .build();

            try {
                SERVICE.addNewStation(station);

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
