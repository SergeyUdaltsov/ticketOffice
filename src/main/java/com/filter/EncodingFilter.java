package com.filter;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static com.utils.UtilConstants.CONTENT_TYPE;
import static com.utils.UtilConstants.ENCODING;

/**
 * Created by Serg on 02.10.2018.
 */
@WebFilter("/*")
public class EncodingFilter implements Filter{

    private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Sets request character encoding to UTF-8
     *
     * @param request  ServletRequest defines an object to provide client request information to a servlet
     * @param response ServletResponse defines an object to assist a servlet in sending a response to the client
     * @param chain           FilterChain, that is used to invoke the resource at the end of the chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {

            request.setCharacterEncoding(ENCODING);
            chain.doFilter(request, response);

        } catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void destroy() {
    }
}
