package com.controller.impl.train;

import com.controller.Command;
import com.dao.DAOFactory;
import com.entity.AbstractEntity;
import com.google.gson.Gson;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 26.09.2018.
 */
public class ShowTrainsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ShowTrainsCommand.class);

    private static final TrainService SERVICE = DAOFactory.getDAOFactory().getTrainService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonTrip");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            int departureStationId = jsonObject.getInt("depSt");
            int arrivalStationId = jsonObject.getInt("arrSt");

           List<AbstractEntity> tours = SERVICE.getTrainsByStations(departureStationId, arrivalStationId);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            response.getWriter().write(new Gson().toJson(tours));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
