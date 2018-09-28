package com.controller.impl.station;

import com.controller.Command;
import com.dao.DAOFactory;
import com.entity.Station;
import com.entity.builder.StationBuilder;
import com.service.StationService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * The {@code AddNewStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new stations.
 */
public class AddNewStationCommand implements Command {


    private static final Logger LOGGER = LogManager.getLogger(AddNewStationCommand.class);

    StationService service = DAOFactory.getDAOFactory().getStationService();


    /**
     * Receives request and response, gets station from request,
     * checks station for existing using and creates new station.
     *
     * if station exists, sets response status 406.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
//
//        try {
//            Email email = new SimpleEmail();
//            email.setHostName("smtp.gmail.com");
//            email.setSmtpPort(465);
//            email.setAuthenticator(new DefaultAuthenticator("sergii.udaltsov@gmail.com", "t883774t"));
//            email.setSSLOnConnect(true);
//            email.setFrom("sergii.udaltsov@gmail.com");
//            email.setSubject("TestMail");
//            email.setMsg("This is a test mail ... :-)\n I sent it to you from my Java app... ");
//            email.addTo("Gudalcova@ukr.net");
//            email.send();
//        } catch (EmailException e) {
//            e.printStackTrace();
//        }


        String jsStr = request.getParameter("jsonStation");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);
            String name = jsonObject.getString("name");

            Station station = new StationBuilder()
                    .buildName(name)
                    .build();

            try {
                service.addNewStation(station);

            } catch (SQLException e) {

                LOGGER.error(e.getMessage());
                response.setStatus(406);
                return;
            }

        } catch (JSONException e) {
            LOGGER.error(e.getMessage());
        }

        response.setStatus(200);

    }
}
