package com.controller.impl.station;

import com.controller.Command;
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

import static com.utils.UtilConstants.*;

/**
 * The {@code AddNewStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new stations.
 */
public class AddNewStationCommand implements Command {


    private static final Logger LOGGER = LogManager.getLogger(AddNewStationCommand.class);

    private final StationService STATION_SERVICE;

    public AddNewStationCommand(StationService STATION_SERVICE) {
        this.STATION_SERVICE = STATION_SERVICE;
    }

    /**
     * Receives request and response, gets station from request,
     * and creates new station.
     * <p>
     * if station exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        try {

            Station station = buildStationFromRequest(request);

            STATION_SERVICE.addNewStation(station);

        } catch (SQLException e) {

            LOGGER.error(STATION_EXISTS);

            response.setStatus(406);
            return;
        }

        response.setStatus(200);

    }

    /**
     * Receives request, creates a station object from request,
     * and returnes it to the method.
     * <p>
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return the instance of {@code Station} entity class
     */
    private Station buildStationFromRequest(HttpServletRequest request) {

        String jsStr = request.getParameter("jsonStation");

        Station station = null;

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsStr);

            String name = jsonObject.getString("name");
            String nameRu = jsonObject.getString("nameRu");

            station = new StationBuilder()
                    .buildName(name)
                    .buildNameRu(nameRu)
                    .build();

        } catch (JSONException e) {

            LOGGER.error(WRONG_DATA_FROM_CLIENT_STATION);
        }

        return station;
    }
}
