package com.controller.impl.route;

import com.controller.Command;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * The {@code DeleteRouteByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible for deleting route with specified id.
 *
 */
public class DeleteRouteByIdCommand implements Command {


    private final RouteService SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(DeleteRouteByIdCommand.class);

    public DeleteRouteByIdCommand(RouteService SERVICE) {
        this.SERVICE = SERVICE;
    }


    /**
     * Receives request and response gets id of route
     * which should be deleted from request and deletes it.
     *
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String jsStr = request.getParameter("stationId");

        int stationId = Integer.parseInt(jsStr);

        try {
            SERVICE.deleteRouteById(stationId);
            response.setStatus(200);
            LOGGER.info(ROUTE + DELETED);

        } catch (SQLException e) {

            LOGGER.error("Route delete error");
            response.setStatus(406);
        }
    }
}
