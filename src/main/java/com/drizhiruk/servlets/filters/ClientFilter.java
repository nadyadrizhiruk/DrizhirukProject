package com.drizhiruk.servlets.filters;

import com.drizhiruk.dao.impl.ClientDBDaoImpl;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.validators.ValidationService;
import com.drizhiruk.validators.impl.ValidationServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

//@WebFilter(urlPatterns = "/clients")
public class ClientFilter implements Filter {

    private ValidationService validationService;

    public ClientFilter(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Map<String, String[]> parameters = request.getParameterMap();

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
                    } ;
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
            PrintWriter writer = response.getWriter();
            writer.println(e.getMessage());
            return;
        }

        chain.doFilter(request, response);

    }
}
