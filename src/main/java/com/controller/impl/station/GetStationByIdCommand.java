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
import static com.utils.UtilData.*;
import static com.utils.UtilConstants.*;

/**
 * The {@code GetStationByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible for retrieving station with specified id from data base.
 */
public class GetStationByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetStationByIdCommand.class);

    private final StationService STATION_SERVICE;

    public GetStationByIdCommand(StationService STATION_SERVICE) {
        this.STATION_SERVICE = STATION_SERVICE;
    }


    /**
     * Receives request and response, gets from request id of station
     * which should be received
     *
     * Sets to the response content the data of station with specified id.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String objStr = request.getParameter("stationId");

        int stationId = Integer.parseInt(objStr);

        Station station = STATION_SERVICE.getStationById(stationId);

        try {
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().write(new Gson().toJson(station));

        } catch (IOException e) {
            LOGGER.error(WRONG_DATA_FROM_CLIENT_STATION);
        }

    }
}
