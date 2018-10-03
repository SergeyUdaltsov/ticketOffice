package com.controller.impl.ticket;

import com.controller.Command;
import com.controller.impl.station.AddNewStationCommand;
import com.entity.Station;
import com.entity.TicketOrder;
import com.entity.User;
import com.entity.builder.StationBuilder;
import com.entity.builder.TicketOrderBuilder;
import com.service.TicketService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * The {@code BuyTicketCommand} class is an implementation of
 * {@code Command} interface, that is responsible for buying tickets by user.
 */
public class BuyTicketsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddNewStationCommand.class);

    TicketService SERVICE;

    public BuyTicketsCommand(TicketService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response, gets data from request:
     * depStId - departure station id, arrStId - arrival station id,
     * creates instance of TicketOrder with all the data for buying tickets.
     * <p>
     * Gets from request session User instance with data of email address and sends a email
     * with the information about tickets were bought.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsRequest");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            int depStId = jsonObject.getInt("depStId");
            int arrStId = jsonObject.getInt("arrStId");

            Station stationFrom = fillUpStation(depStId);

            Station stationTo = fillUpStation(arrStId);

            User user = (User) request.getSession().getAttribute("user");


            TicketOrder order = fillUpTicketOrder(request, user, stationFrom, stationTo);

            try {

                SERVICE.buyTickets(order);

            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }

        } catch (JSONException e) {
            LOGGER.error(WRONG_DATA_FROM_CLIENT_TICKET);
        }

    }

    /**
     * Creates new station instance with specified id.
     * */
    private Station fillUpStation(int stationId) {

        return new StationBuilder()
                .buildId(stationId)
                .build();
    }

    /**
     * Collects all the data from request and prepared data from process metod and creates
     * the instance of TicketOrder for buying tickets
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param user {@code User} from {@code process} method
     * @param stationFrom {@code Station} from {@code process} method
     * @param stationTo {@code Station} from {@code process} method
     * */
    private TicketOrder fillUpTicketOrder(HttpServletRequest request, User user,
                                          Station stationFrom, Station stationTo) throws JSONException {

        String jsStr = request.getParameter("jsRequest");

        JSONObject jsonObject = new JSONObject(jsStr);

        int ecoCount = jsonObject.getInt("ecoCountToBuy");
        int busCount = jsonObject.getInt("busCountToBuy");
        int comCount = jsonObject.getInt("comCountToBuy");
        int routeId = jsonObject.getInt("routeId");

        return new TicketOrderBuilder()
                .buildUser(user)
                .buildStationFrom(stationFrom)
                .buildStationTo(stationTo)
                .buildRouteId(routeId)
                .buildEconomyCount(ecoCount)
                .buildBusinessCount(busCount)
                .buildComfortCount(comCount)
                .build();
    }
}
