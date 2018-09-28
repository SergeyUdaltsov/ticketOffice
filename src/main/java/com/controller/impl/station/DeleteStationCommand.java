package com.controller.impl.station;

import com.controller.Command;
import com.dao.DAOFactory;
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
 * The {@code DeleteStationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for deleting stations.
 */
public class DeleteStationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteStationCommand.class);

    StationService service = DAOFactory.getDAOFactory().getStationService();

    /**
     * Receives request and response, gets station id from request,
     * checks station for existing and deletes station.
     * <p>
     * if station does not exist, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("stationId");

        int stationId = Integer.parseInt(jsStr);

        try {

            service.deleteStationById(stationId);
            response.setStatus(200);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_DELETE_STATION);
            response.setStatus(406);
        }


    }
}

