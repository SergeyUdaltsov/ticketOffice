package com.controller.command.impl.user;

import com.controller.command.Command;
import com.dao.DAOFactory;
import com.entity.User;
import com.google.gson.Gson;
import com.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * The {@code ValidateUserPasswordCommand} class is an implementation of
 * {@code Command} interface, that is responsible for validating user's login and password.
 */
public class ValidateUserPasswordCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ValidateUserPasswordCommand.class);

    UserService service = DAOFactory.getDAOFactory().getUserService();

    /**
     * Receives request and response, gets user email and password from request,
     * gets user from db using {@code validateUser() method} and checks for matching email to password.
     *
     * if user exists or password does not match the email sets response status 406.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String jsStr = request.getParameter("jsonLogin");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");

            User user = service.validateUser(email, password);

            if (Objects.isNull(user)){
                response.setStatus(406);
                return;
            }

            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(user));

            } catch (IOException e) {
               LOGGER.error(e.getMessage());
            }

        } catch (JSONException e) {
            LOGGER.error(e.getMessage());
        }

    }
}
