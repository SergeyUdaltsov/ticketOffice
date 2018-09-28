package com.controller.impl.route;

import com.controller.Command;
import com.dao.DAOFactory;
import com.entity.Route;
import com.google.gson.Gson;
import com.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.utils.UtilConstants.*;

/**
 * The {@code GetAllRoutesCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting the list of all routes.
 */
public class GetAllRoutesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetAllRoutesCommand.class);

    RouteService service = DAOFactory.getDAOFactory().getRouteService();//final

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        List<Route> routes = service.getAllRoutes();

        response.setContentType("application/json");//move to constants
        response.setCharacterEncoding("UTF-8");

        try {

            response.getWriter().write(new Gson().toJson(routes));

        } catch (IOException e) {
            LOGGER.error(COULD_NOT_LOAD_ROUTES);
        }


    }
}
