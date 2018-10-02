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
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code AddNewStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for retrieving list of all stations from data base.
 */
public class GetAllStationsCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger(GetAllStationsCommand.class);

    private final StationService STATION_SERVICE;

    public GetAllStationsCommand(StationService STATION_SERVICE) {
        this.STATION_SERVICE = STATION_SERVICE;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        List<Station> stations = STATION_SERVICE.getAllStations();

        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);

        try {

            response.getWriter().write(new Gson().toJson(stations));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
