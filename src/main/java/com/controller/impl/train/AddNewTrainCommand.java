package com.controller.impl.train;

import com.controller.Command;
import com.dao.DAOFactory;
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

    private static final TrainService SERVICE = DAOFactory.getDAOFactory().getTrainService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonTrain");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String name = jsonObject.getString("name");
            int economyNumber = jsonObject.getInt("economy");
            int businessNumber = jsonObject.getInt("business");
            int comfortNumber = jsonObject.getInt("comfort");


            Train train = new TrainBuilder()
                    .buildName(name)
                    .buildEconomy(economyNumber)
                    .buildBusiness(businessNumber)
                    .buildComfort(comfortNumber)
                    .build();

            try {

                SERVICE.addNewTrain(train);

                LOGGER.info(TRAIN + train.getName() + CREATED);
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
