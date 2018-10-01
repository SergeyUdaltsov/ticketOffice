package com.controller.impl.station;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.entity.Station;
import com.entity.builder.StationBuilder;
import com.service.StationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import static com.utils.UtilConstants.*;

/**
 * Created by Serg on 22.09.2018.
 */
public class UpdateStationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UpdateStationCommand.class);

    private final StationService STATION_SERVICE;

    public UpdateStationCommand(StationService STATION_SERVICE) {
        this.STATION_SERVICE = STATION_SERVICE;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonStation");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String stationName = jsonObject.getString("name");

            int stationId = jsonObject.getInt("id");

            Station station = new StationBuilder()
                    .buildId(stationId)
                    .buildName(stationName)
                    .build();

            try {

                STATION_SERVICE.updateStation(station);

            } catch (SQLException e) {

                LOGGER.error(e.getMessage());
                response.setStatus(406);
                return;
            }

        } catch (JSONException e) {
            LOGGER.error(WRONG_DATA_FROM_CLIENT_STATION);
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }
}
