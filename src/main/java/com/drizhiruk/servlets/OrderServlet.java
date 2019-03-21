package com.drizhiruk.servlets;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.services.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrderServlet extends HttpServlet {

    private OrderService orderService;
    private ClientService clientService;
    private ProductService productService;

    public OrderServlet(OrderService orderService, ClientService clientService1, ProductService productService) {
        this.orderService = orderService;
        this.clientService = clientService1;
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        String clientId = req.getParameter("clientId");
        Client client = clientService.findById(Long.parseLong(clientId));
        String date = req.getParameter("date");
        Order order = orderService.createOrderObject(client,date,new ArrayList<>());

        Map<String, String[]> parameters = req.getParameterMap();

        for (int i=1; parameters.containsKey("productId"+i); i++) {

            String productId = req.getParameter("productId"+i);
            String price = req.getParameter("price"+i);
            String amount = req.getParameter("amount"+i);

            Product product= productService.findById(Long.parseLong(productId));
            ProductInOrder productInOrder= new ProductInOrder(product,new BigDecimal(price),Integer.parseInt(amount),order);

            order.addToProducts(productInOrder);
        }
        orderService.saveNewOrder(order);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        String id = req.getParameter("id");
        String clientId = req.getParameter("clientId");
        String date = req.getParameter("date");

        Order order = orderService.findById(Long.parseLong(id));
        Client client = clientService.findById(Long.parseLong(clientId));

        Map<String, String[]> parameters = req.getParameterMap();

        order.setProducts(new ArrayList<>());

        for (int i=1; parameters.containsKey("productId"+i); i++) {

            String productId = req.getParameter("productId"+i);
            String price = req.getParameter("price"+i);
            String amount = req.getParameter("amount"+i);

            Product product= productService.findById(Long.parseLong(productId));
            ProductInOrder productInOrder= new ProductInOrder(product,new BigDecimal(price),Integer.parseInt(amount),order);

            order.addToProducts(productInOrder);
        }
        orderService.modifyOrder(order,client,date);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String[]> parameters = req.getParameterMap();

        if (parameters.containsKey("id")) {
            PrintWriter writer = resp.getWriter();
            for (String id : parameters.get("id")) {
                Order order = orderService.findById(Long.parseLong(id));
                writer.println("<h1>" + order + "</h1>");
                writer.println("<br>");
            }
        } else if (parameters.containsKey("clientId")) {
            String clientId = req.getParameter("clientId");
            Client client = clientService.findById(Long.parseLong(clientId));
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            for (Order order : orderService.findAllOrdersByClient(client)) {
                writer.println("<h1>" + client + "</h1>");
                writer.println("<br>");
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        orderService.removeOrder(id);
    }
}
