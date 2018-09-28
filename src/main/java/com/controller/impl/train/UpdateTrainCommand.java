package com.controller.impl.train;

import com.controller.Command;
import com.dao.DAOFactory;
import com.entity.Station;
import com.entity.Train;
import com.entity.builder.StationBuilder;
import com.entity.builder.TrainBuilder;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.WRONG_DATA_FROM_CLIENT;

/**
 * Created by Serg on 25.09.2018.
 */
public class UpdateTrainCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllTrainsCommand.class);

    TrainService service = DAOFactory.getDAOFactory().getTrainService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonTrain");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String trainName = jsonObject.getString("name");

            int trainId = jsonObject.getInt("id");
            int econCount = jsonObject.getInt("econCount");
            int busCount = jsonObject.getInt("busCount");
            int comfCount = jsonObject.getInt("comfCount");

            Train train = new TrainBuilder()
                    .buildId(trainId)
                    .buildName(trainName)
                    .buildEconomy(econCount)
                    .buildBusiness(busCount)
                    .buildComfort(comfCount)
                    .build();

            try {

                service.updateTrain(train);

            } catch (SQLException e) {

                LOGGER.error(e.getMessage());
                response.setStatus(406);
                return;
            }

        } catch (JSONException e) {
            LOGGER.error(WRONG_DATA_FROM_CLIENT);
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }
}
