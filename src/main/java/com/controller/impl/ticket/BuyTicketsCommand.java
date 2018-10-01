package com.controller.impl.ticket;

import com.controller.Command;
import com.controller.impl.station.AddNewStationCommand;
import com.entity.Station;
import com.entity.TicketOrder;
import com.entity.User;
import com.entity.builder.StationBuilder;
import com.entity.builder.TicketOrderBuilder;
import com.entity.builder.UserBuilder;
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
 * Created by Serg on 29.09.2018.
 */
public class BuyTicketsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddNewStationCommand.class);

    TicketService SERVICE;

    public BuyTicketsCommand(TicketService service) {
        this.SERVICE = service;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsRequest");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            int depStId = jsonObject.getInt("depStId");
            int arrStId = jsonObject.getInt("arrStId");

            User user = fillUpUser(request);

            Station stationFrom = fillUpStation(depStId);

            Station stationTo = fillUpStation(arrStId);

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


    private User fillUpUser(HttpServletRequest request) throws JSONException {

        String jsStr = request.getParameter("jsRequest");

        JSONObject jsonObject = new JSONObject(jsStr);

        String userFirstName = jsonObject.getString("userFirstName");
        String userLastName = jsonObject.getString("userLastName");
        String userEmail = jsonObject.getString("userEmail");

        User user = new UserBuilder()
                .buildFirstName(userFirstName)
                .buildLastName(userLastName)
                .buildEmail(userEmail)
                .build();

        return user;
    }

    private Station fillUpStation(int stationId) {

        Station station = new StationBuilder()
                .buildId(stationId)
                .build();

        return station;
    }

    private TicketOrder fillUpTicketOrder(HttpServletRequest request, User user,
                                          Station stationFrom, Station stationTo) throws JSONException {

        String jsStr = request.getParameter("jsRequest");

        JSONObject jsonObject = new JSONObject(jsStr);

        int ecoCount = jsonObject.getInt("ecoCountToBuy");
        int busCount = jsonObject.getInt("busCountToBuy");
        int comCount = jsonObject.getInt("comCountToBuy");
        int routeId = jsonObject.getInt("routeId");

        TicketOrder order = new TicketOrderBuilder()
                .buildUser(user)
                .buildStationFrom(stationFrom)
                .buildStationTo(stationTo)
                .buildRouteId(routeId)
                .buildEconomyCount(ecoCount)
                .buildBusinessCount(busCount)
                .buildComfortCount(comCount)
                .build();

        return order;
    }
}