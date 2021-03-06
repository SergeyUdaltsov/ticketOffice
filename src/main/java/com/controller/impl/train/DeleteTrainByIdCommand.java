package com.controller.impl.train;

import com.controller.Command;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.utils.UtilConstants.*;

/**
 * The {@code DeleteTrainByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible for deleting train with
 * specified id.
 */
public class DeleteTrainByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteTrainByIdCommand.class);

    private final TrainService SERVICE;

    public DeleteTrainByIdCommand(TrainService service) {
        this.SERVICE = service;
    }


    /**
     * Receives request and response, gets from request id of Train which should
     * be deleted from data base.
     *
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("trainId");

        int trainId = Integer.parseInt(jsStr);

        try {

            SERVICE.deleteTrainById(trainId);

            LOGGER.info(TRAIN + trainId + DELETED);

            response.setStatus(200);

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_DELETE_TRAIN);
            response.setStatus(406);
        }
    }
}
