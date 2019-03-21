package com.drizhiruk.servlets;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.dao.impl.ClientDBDaoImpl;
import com.drizhiruk.dao.impl.OrderDBDaoImpl;
import com.drizhiruk.dao.impl.ProductDBDaoImpl;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.services.ProductService;
import com.drizhiruk.services.impl.ClientServiceImpl;
import com.drizhiruk.services.impl.OrderServiceImpl;
import com.drizhiruk.services.impl.ProductServiceImpl;
import com.drizhiruk.servlets.filters.ClientFilter;
import com.drizhiruk.servlets.filters.OrderFilter;
import com.drizhiruk.servlets.filters.ProductFilter;
import com.drizhiruk.validators.ValidationService;
import com.drizhiruk.validators.impl.ValidationServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebApp implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ClientDao clientDao = new ClientDBDaoImpl();
        ProductDao productDao = new ProductDBDaoImpl();
        OrderDao orderDao = new OrderDBDaoImpl();
        ValidationService validationService = new ValidationServiceImpl(clientDao, productDao, orderDao);
        ClientService clientService = new ClientServiceImpl(clientDao, validationService);
        ProductService productService = new ProductServiceImpl(productDao,validationService);
        OrderService orderService = new OrderServiceImpl(orderDao,validationService);
        ServletContext servletContext = sce.getServletContext();
        servletContext
                .addServlet("ClientServlet", new ClientServlet(clientService))
                .addMapping("/clients/*");
        servletContext
                .addServlet("ProductServlet", new ProductServlet(productService))
                .addMapping("/products/*");
        servletContext
                .addServlet("OrderServlet", new OrderServlet(orderService, clientService, productService))
                .addMapping("/orders/*");

        servletContext
                .addFilter("ClientFilter", new ClientFilter(validationService))
                .addMappingForServletNames(null,false,"ClientServlet");
        servletContext
                .addFilter("ProductFilter", new ProductFilter(validationService))
                .addMappingForServletNames(null,false,"ProductServlet");
        servletContext
                .addFilter("OrderFilter", new OrderFilter(validationService))
                .addMappingForServletNames(null,false,"OrderServlet");

    }
}
