package com.drizhiruk.servlets.filters;

import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.validators.ValidationService;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

//@WebFilter(urlPatterns = "/clients")
public class ProductFilter implements Filter {

    private ValidationService validationService;

    public ProductFilter(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Map<String, String[]> parameters = request.getParameterMap();

        try {
            if (parameters.containsKey("id")) {
                for (String id : parameters.get("id")) {
                    validationService.checkExistenceProductId(Long.parseLong(id));
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
