
package com.drizhiruk.controllers.filters;

import com.drizhiruk.dao.impl.ClientDBDaoImpl;
import com.drizhiruk.dao.impl.OrderDBDaoImpl;
import com.drizhiruk.dao.impl.ProductDBDaoImpl;
import com.drizhiruk.exceptions.BisnessException;

import com.drizhiruk.validators.ValidationService;
import com.drizhiruk.validators.impl.ValidationServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ClientFilter implements Filter {

    private ValidationService validationService;

    public ClientFilter() {
        this.validationService = new ValidationServiceImpl(new ClientDBDaoImpl(),
                new ProductDBDaoImpl(),
                new OrderDBDaoImpl());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod();

        Map<String, String[]> parameters = servletRequest.getParameterMap();

        try {
            if (parameters.containsKey("age")) {
                for (String age : parameters.get("age")) {
                    validationService.validateAge(Integer.parseInt(age));
                }
            }
            if (parameters.containsKey("phone")) {
                for (String phone : parameters.get("phone")) {
                    validationService.validatePhone(phone);
                    if (!(parameters.containsKey("id"))) {
                        validationService.checkExistence(phone);
                    }
                }
            }
            if (parameters.containsKey("email")) {
                for (String email : parameters.get("email")) {
                    validationService.validateEmail(email);
                }
            }
            if (parameters.containsKey("id")) {
                for (String id : parameters.get("id")) {
                    validationService.checkExistenceClientId(Long.parseLong(id));
                }
            }
        } catch (NumberFormatException | BisnessException e) {
            PrintWriter writer = servletResponse.getWriter();
            writer.println(e.getMessage());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}



