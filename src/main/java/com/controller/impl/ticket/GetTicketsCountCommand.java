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
 * Created by Serg on 27.09.2018.
 */
public class GetTicketsCountCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetTicketsCountCommand.class);

    private final TicketService SERVICE;

    public GetTicketsCountCommand(TicketService service) {
        this.SERVICE = service;
    }

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
