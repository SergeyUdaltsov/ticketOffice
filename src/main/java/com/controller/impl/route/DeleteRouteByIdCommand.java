package com.controller.impl.route;

import com.controller.Command;
import com.dao.DAOFactory;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.COULD_NOT_DELETE_STATION;
import static com.utils.UtilConstants.INTERMEDIATE_STATION_DELETED;

/**
 * The {@code DeleteRouteByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible to delete route with specified id.
 */
public class DeleteRouteByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteRouteByIdCommand.class);

    RouteService service = DAOFactory.getDAOFactory().getRouteService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String jsStr = request.getParameter("stationId");

        int stationId = Integer.parseInt(jsStr);

        try {
            service.deleteRouteById(stationId);
            response.setStatus(200);
            LOGGER.info(INTERMEDIATE_STATION_DELETED);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_DELETE_STATION);
            response.setStatus(406);
        }
    }
}
