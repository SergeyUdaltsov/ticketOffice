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
 * The {@code UpdateStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for updating data of station with specified
 * id in data base.
 */
public class UpdateStationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UpdateStationCommand.class);

    private final StationService STATION_SERVICE;

    public UpdateStationCommand(StationService service) {
        this.STATION_SERVICE = service;
    }


    /**
     * Receives request and response, creates instance of station which should be persisted
     * to data base. Gets the id of station and updates it with the new data.
     *
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        Station station = buildStationFromRequest(request, response);

        try {

            STATION_SERVICE.updateStation(station);

        } catch (SQLException e) {

            LOGGER.error(WRONG_DATA_FROM_CLIENT_STATION);
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request, creates instance of station which should be persisted
     * to data base.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     * @return the instance of {@code Station} entity class
     */
    private Station buildStationFromRequest(HttpServletRequest request, HttpServletResponse response) {

        Station station = null;
        String jsStr = request.getParameter("jsonStation");

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsStr);

            String stationName = jsonObject.getString("name");
            String stationNameRu = jsonObject.getString("nameRu");

            int stationId = jsonObject.getInt("id");

            station = new StationBuilder()
                    .buildId(stationId)
                    .buildName(stationName)
                    .buildNameRu(stationNameRu)
                    .build();
        } catch (JSONException e) {

            response.setStatus(406);
            LOGGER.error(WRONG_DATA_FROM_CLIENT_STATION);
        }

        return station;
    }
}
