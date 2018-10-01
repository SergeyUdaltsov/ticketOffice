package com.controller.impl.station;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * The {@code DeleteStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for deleting stations.
 */
public class DeleteStationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteStationCommand.class);

    private final StationService STATION_SERVICE;

    public DeleteStationCommand(StationService STATION_SERVICE) {
        this.STATION_SERVICE = STATION_SERVICE;
    }

    /**
     * Receives request and response, gets station id from request,
     * checks station for existing and deletes station.
     * <p>
     * if station does not exist, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("stationId");

        int stationId = Integer.parseInt(jsStr);

        try {

            STATION_SERVICE.deleteStationById(stationId, false);
            response.setStatus(200);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_DELETE_STATION);
            response.setStatus(406);
        }


    }
}

