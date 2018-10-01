package com.controller.impl.train;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.entity.Train;
import com.entity.builder.TrainBuilder;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 25.09.2018.
 */
public class UpdateTrainCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllTrainsCommand.class);

    private final TrainService SERVICE;

    public UpdateTrainCommand(TrainService service) {
        this.SERVICE = service;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        try {
            Train train = getTrainFromRequest(request, response);

            SERVICE.updateTrain(train);

            LOGGER.info(TRAIN + train.getName() + UPDATED);

        } catch (SQLException e) {

            LOGGER.error(TRAIN_ERROR_UPDATE);
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    private Train getTrainFromRequest(HttpServletRequest request, HttpServletResponse response) {

        Train train = new Train();
        String jsStr = request.getParameter("jsonTrain");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String trainName = jsonObject.getString("name");

            int trainId = jsonObject.getInt("id");
            int econCount = jsonObject.getInt("econCount");
            int busCount = jsonObject.getInt("busCount");
            int comfCount = jsonObject.getInt("comfCount");

            train = new TrainBuilder()
                    .buildId(trainId)
                    .buildName(trainName)
                    .buildEconomy(econCount)
                    .buildBusiness(busCount)
                    .buildComfort(comfCount)
                    .build();


        } catch (JSONException e) {

            response.setStatus(406);
            LOGGER.error(WRONG_DATA_FROM_CLIENT_TRAIN);
        }
        return train;
    }
}
