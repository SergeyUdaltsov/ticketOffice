package com.controller.impl.route;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.service.RouteService;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * The {@code DeleteIntermediateStationByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new route.
 */
public class DeleteIntermediateStationByIdCommand implements Command {

    private final StationService SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(DeleteIntermediateStationByIdCommand.class);

    public DeleteIntermediateStationByIdCommand(StationService service) {
        this.SERVICE = service;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("stationId");

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
