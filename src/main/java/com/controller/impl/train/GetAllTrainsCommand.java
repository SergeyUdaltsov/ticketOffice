package com.controller.impl.train;

import com.controller.Command;
import com.entity.Train;
import com.google.gson.Gson;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code GetAllTrainsCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting the list of all the trains.
 */
public class GetAllTrainsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllTrainsCommand.class);

    private final TrainService SERVICE;

    public GetAllTrainsCommand(TrainService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response, sets to the response content list of all trains from data base.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        List<Train> trains = SERVICE.getAllTrains();

        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);

        try {

            response.getWriter().write(new Gson().toJson(trains));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
