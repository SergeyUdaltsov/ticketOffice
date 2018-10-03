package com.controller.impl.route;

import com.controller.Command;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * The {@code DeleteIntermediateStationByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible for deleting intermediate
 * station by it's ID.
 */
public class DeleteIntermediateStationByIdCommand implements Command {

    private static final String STATION_ID = "stationId";
    private final StationService SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(DeleteIntermediateStationByIdCommand.class);

    public DeleteIntermediateStationByIdCommand(StationService service) {
        this.SERVICE = service;
    }


    /**
     * Receives request and response gets id of station
     * which should be deleted from request and deletes it.
     *
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter(STATION_ID);

        int stationId = Integer.parseInt(jsStr);

        try {
            SERVICE.deleteStationById(stationId, true);

            response.setStatus(200);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_DELETE_STATION);
            response.setStatus(406);
        }
    }
}
