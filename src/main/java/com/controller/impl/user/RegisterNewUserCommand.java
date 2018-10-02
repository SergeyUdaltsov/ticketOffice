package com.controller.impl.user;

import com.controller.Command;
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

import static com.utils.UtilConstants.*;

/**
 * The {@code RegisterNewUserCommand} class is an implementation of
 * {@code Command} interface, that is responsible for registering new users.
 */
public class RegisterNewUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RegisterNewUserCommand.class);

    private final UserService SERVICE;

    public RegisterNewUserCommand(UserService service) {
        this.SERVICE = service;
    }

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

        try {
            User user = getUserFromRequest(request);

            SERVICE.createNewUser(user);



        } catch (SQLException e) {

            LOGGER.error(USER_EXISTS);
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    private User getUserFromRequest(HttpServletRequest request) {

        User user = new User();

        String jsStr = request.getParameter("jsonUser");

        try {
            JSONObject jsonObject = new JSONObject(jsStr);
            String email = jsonObject.getString("email");

            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String password = jsonObject.getString("password");

            user = new UserBuilder()
                    .buildFirstName(firstName)
                    .buildLastName(lastName)
                    .buildEmail(email)
                    .buildPassword(password)
                    .build();


        } catch (JSONException e) {
            LOGGER.error(WRONG_DATA_FROM_CLIENT_USER);
        }
        return user;
    }
}
