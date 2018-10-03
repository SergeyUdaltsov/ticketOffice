package com.controller.impl.train;

import com.controller.Command;
import com.entity.Train;
import com.google.gson.Gson;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.utils.UtilConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code GetTrainByIdCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting data of train with specified id.
 */
public class GetTrainByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllTrainsCommand.class);

    private final TrainService SERVICE;

    public GetTrainByIdCommand(TrainService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response, gets from request the id of train which should be retrieved.
     * Gets from data base data of train with specified id and sets the instance of train
     * to the response content.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String objStr = request.getParameter("trainId");

        int trainId = Integer.parseInt(objStr);

        Train train = SERVICE.getTrainById(trainId);

        try {
            response.setCharacterEncoding(ENCODING);

            response.setContentType(CONTENT_TYPE);
            response.getWriter().write(new Gson().toJson(train));

        } catch (IOException e) {
            LOGGER.error(COULD_NOT_LOAD_TRAINS);
        }
    }
}
