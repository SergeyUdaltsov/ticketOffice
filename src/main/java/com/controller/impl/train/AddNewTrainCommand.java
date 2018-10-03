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
 * The {@code AddNewTrainCommand} class is an implementation of
 * {@code Command} interface, that is responsible to creating new train.
 */
public class AddNewTrainCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddNewTrainCommand.class);

    private final TrainService SERVICE;

    public AddNewTrainCommand(TrainService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response, creates from request instance of Train which should
     * be persisted to the data base.
     *
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        try {
            Train train = buildTrainFromRequest(request, response);

            SERVICE.addNewTrain(train);

            LOGGER.info(TRAIN + train.getName() + CREATED);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Method responsible for the creating of instance of Train
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     * */
    private Train buildTrainFromRequest(HttpServletRequest request, HttpServletResponse response) {

        Train train = new Train();

        String jsStr = request.getParameter("jsonTrain");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String name = jsonObject.getString("name");
            String nameRu = jsonObject.getString("nameRu");
            int economyNumber = jsonObject.getInt("economy");
            int businessNumber = jsonObject.getInt("business");
            int comfortNumber = jsonObject.getInt("comfort");

            train = new TrainBuilder()
                    .buildName(name)
                    .buildNameRu(nameRu)
                    .buildEconomy(economyNumber)
                    .buildBusiness(businessNumber)
                    .buildComfort(comfortNumber)
                    .build();

        } catch (JSONException e) {

            response.setStatus(406);

            LOGGER.error(WRONG_DATA_FROM_CLIENT_TRAIN);
        }

        return train;
    }

}
