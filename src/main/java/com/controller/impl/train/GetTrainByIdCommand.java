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
 * Created by Serg on 25.09.2018.
 */
public class GetTrainByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllTrainsCommand.class);

    private final TrainService SERVICE;

    public GetTrainByIdCommand(TrainService service) {
        this.SERVICE = service;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String objStr = request.getParameter("trainId");

        int trainId = Integer.parseInt(objStr);

        Train train = SERVICE.getTrainById(trainId);

        try {

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().write(new Gson().toJson(train));

        } catch (IOException e) {
            LOGGER.error(COULD_NOT_LOAD_TRAINS);
        }
    }
}
