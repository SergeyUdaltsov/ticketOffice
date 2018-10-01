package com.controller.impl.ticket;

import com.controller.Command;
import com.controller.impl.station.AddNewStationCommand;
import com.dao.factory.DAOFactory;
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

/**
 * Created by Serg on 29.09.2018.
 */
public class BuyTicketsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddNewStationCommand.class);

    private static final TicketService SERVICE = DAOFactory.getDAOFactory().getTicketService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsRequest");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            int ecoCount = jsonObject.getInt("ecoCountToBuy");
            int busCount = jsonObject.getInt("busCountToBuy");
            int comCount = jsonObject.getInt("comCountToBuy");
            int routeId = jsonObject.getInt("routeId");
            int depStId = jsonObject.getInt("depStId");
            int arrStId = jsonObject.getInt("arrStId");
            String userFirstName = jsonObject.getString("userFirstName");
            String userLastName = jsonObject.getString("userLastName");
            String userEmail = jsonObject.getString("userEmail");

            User user = fillUpUser(userFirstName, userLastName, userEmail);

            Station stationFrom = fillUpStation(depStId);

            Station stationTo = fillUpStation(arrStId);

            TicketOrder order = fillUpTicketOrder(user, stationFrom, stationTo, routeId, ecoCount, busCount, comCount);

            try {

                SERVICE.buyTickets(order);

            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private User fillUpUser(String firstName, String lastName, String email){

        User user = new UserBuilder()
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildEmail(email)
                .build();

        return user;
    }

    private Station fillUpStation(int stationId) {

        Station station = new StationBuilder()
                .buildId(stationId)
                .build();

        return station;
    }

    private TicketOrder fillUpTicketOrder(User user, Station stationFrom, Station stationTo, int routeId,
                                          int ecoCount, int busCount, int comCount) {

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
