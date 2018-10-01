package com.controller.impl.user;

import com.controller.Command;
import com.dao.factory.DAOFactory;
import com.entity.User;
import com.entity.builder.UserBuilder;
import com.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * The {@code RegisterNewUserCommand} class is an implementation of
 * {@code Command} interface, that is responsible for registering new users.
 */
public class RegisterNewUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RegisterNewUserCommand.class);
    UserService service = DAOFactory.getDAOFactory().getUserService();

    /**
     * Receives request and response gets user from request,
     * checks user for existing using {@code checkIfUserExists() method} and register new user
     * <p>
     * if user exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        String jsStr = request.getParameter("jsonUser");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);
            String email = jsonObject.getString("email");

            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String password = jsonObject.getString("password");

            User user = new UserBuilder()
                    .buildFirstName(firstName)
                    .buildLastName(lastName)
                    .buildEmail(email)
                    .buildPassword(password)
                    .build();

            try {

                service.createNewUser(user);

            } catch (SQLException e) {

                LOGGER.error(e.getMessage());
                response.setStatus(406);
                return;
            }

        } catch (JSONException e) {
            LOGGER.error(e.getMessage());
        }

        response.setStatus(200);
    }
}
