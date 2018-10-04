package com.controller.impl.train;

import com.controller.Command;
import com.entity.Tour;
import com.google.gson.Gson;
import com.service.TrainService;
import com.utils.UtilConstants;
import com.utils.UtilData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code ShowTrainsBetweenStationsCommand} class is an implementation of
 * {@code Command} interface, that is responsible for retrieving from data base
 * the list of trains with its data which go through the specified stations.
 */
public class ShowTrainsBetweenStationsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ShowTrainsBetweenStationsCommand.class);

    private final TrainService SERVICE;

    public ShowTrainsBetweenStationsCommand(TrainService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response, gets from request the data of departure and arrival stations,
     * and sets to the response content list of all the trains which go through between specified stations.
     *
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonTrip");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            int departureStationId = jsonObject.getInt("depSt");
            int arrivalStationId = jsonObject.getInt("arrSt");
            String depDateStr = jsonObject.getString("date");

            LocalDate depDate = (depDateStr.equals("")) ? LocalDate.now() : LocalDate.parse(depDateStr);

           List<Tour> tours = SERVICE.getTrainsByStations(departureStationId, arrivalStationId, depDate);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            response.getWriter().write(new Gson().toJson(tours));

        } catch (JSONException e) {
            LOGGER.error(WRONG_DATA_FROM_CLIENT_ROUTE);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
