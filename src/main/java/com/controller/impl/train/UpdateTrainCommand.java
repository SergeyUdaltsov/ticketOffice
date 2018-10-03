package com.controller.impl.train;

import com.controller.Command;
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
 * The {@code UpdateTrainCommand} class is an implementation of
 * {@code Command} interface, that is responsible for updating the specified train.
 */
public class UpdateTrainCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllTrainsCommand.class);

    private final TrainService SERVICE;

    public UpdateTrainCommand(TrainService service) {
        this.SERVICE = service;
    }


    /**
     * Receives request and response, gets from the request data of train which should be updated.
     * Updates the specified train in data base.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
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

    /**
     * Method responsible for creating the instance of Train from the data received in request
     * Sets status of response 406 if could not create Train from the data received from client side.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     * @return train {@code Train} instance
     * */
    private Train getTrainFromRequest(HttpServletRequest request, HttpServletResponse response) {

        Train train = new Train();
        String jsStr = request.getParameter("jsonTrain");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String trainName = jsonObject.getString("name");
            String trainNameRu = jsonObject.getString("nameRu");

            int trainId = jsonObject.getInt("id");
            int econCount = jsonObject.getInt("econCount");
            int busCount = jsonObject.getInt("busCount");
            int comfCount = jsonObject.getInt("comfCount");

            train = new TrainBuilder()
                    .buildId(trainId)
                    .buildName(trainName)
                    .buildNameRu(trainNameRu)
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
