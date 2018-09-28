package com.controller.impl.train;

import com.controller.Command;
import com.dao.DAOFactory;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.COULD_NOT_DELETE_STATION;

/**
 * Created by Serg on 25.09.2018.
 */
public class DeleteTrainByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteTrainByIdCommand.class);

    TrainService service = DAOFactory.getDAOFactory().getTrainService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("trainId");

        int trainId = Integer.parseInt(jsStr);

        try {

            service.deleteTrainById(trainId);
            response.setStatus(200);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_DELETE_STATION);
            response.setStatus(406);
        }
    }
}
