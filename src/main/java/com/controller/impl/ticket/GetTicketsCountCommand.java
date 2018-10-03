package com.controller.impl.ticket;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.google.gson.Gson;
import com.service.TicketService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code GetTicketsCountCommand} class is an implementation of
 * {@code Command} interface, that is responsible for calculating tickets count
 * available for buying to the specified train between specified stations.
 */
public class GetTicketsCountCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetTicketsCountCommand.class);

    private final TicketService SERVICE;

    public GetTicketsCountCommand(TicketService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response, gets data from request:
     * depStId - departure station id, arrStId - arrival station id.
     *
     * Calculates count of available tickets and sets to the response
     * the list of three numbers: count of economy places, count of business places
     * and count of comfort places.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsRouteId = request.getParameter("routeId");
        String jsDepStId = request.getParameter("depSt");
        String jsArrStId = request.getParameter("arrSt");

        int routeId = Integer.parseInt(jsRouteId);
        int depStId = Integer.parseInt(jsDepStId);
        int arrStId = Integer.parseInt(jsArrStId);

        try {

            List<Integer> seats = SERVICE.getTicketCount(routeId, depStId, arrStId);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            response.getWriter().write(new Gson().toJson(seats));

        } catch (SQLException | IOException e) {
            LOGGER.error(COULD_NOT_LOAD_TICKETS);
        }

        response.setStatus(200);

    }


}
