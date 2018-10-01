package com.controller.impl.station;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.entity.Station;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.utils.UtilData.*;

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

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String objStr = request.getParameter("stationId");

        int stationId = Integer.parseInt(objStr);

        Station station = STATION_SERVICE.getStationById(stationId);

        try {

            sendJson(station, response);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }
}
