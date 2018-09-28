package com.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code Command} interface is a controller interface, that defines
 * behaviour of each controller, that implements this interface.
 */
public interface Command {

    /**
     * Responsible for processing request and sending response.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    void process(HttpServletRequest request, HttpServletResponse response);
}
