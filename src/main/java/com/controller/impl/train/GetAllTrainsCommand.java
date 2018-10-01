package com.controller.impl.train;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.entity.Train;
import com.google.gson.Gson;
import com.service.TrainService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Serg on 25.09.2018.
 */
public class GetAllTrainsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllTrainsCommand.class);

    TrainService service = DAOFactory.getDAOFactory().getTrainService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        List<Train> trains = service.getAllTrains();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            response.getWriter().write(new Gson().toJson(trains));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
