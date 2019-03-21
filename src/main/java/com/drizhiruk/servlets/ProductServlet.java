package com.drizhiruk.servlets;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Product;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;


public class ProductServlet extends HttpServlet {

    private ProductService productService;

    public ProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String amount = req.getParameter("amount");
        productService.createProduct(name, new BigDecimal(price), Integer.parseInt(amount));
        }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String amount = req.getParameter("amount");
        Product product = productService.findById(Long.parseLong(id));
        productService.modifyProduct(product, name, new BigDecimal(price), Integer.parseInt(amount));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String[]> parameters = req.getParameterMap();
        if (parameters.containsKey("id")) {
            PrintWriter writer = resp.getWriter();
            for (String id : parameters.get("id")) {
                Product product = productService.findById(Long.parseLong(id));
                writer.println("<h1>" + product + "</h1>");
                writer.println("<br>");
            }
        } else {
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            for (Product product : productService.getListOfAllProducts()) {
                writer.println("<h1>" + product + "</h1>");
                writer.println("<br>");

            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        productService.removeProduct(id);
    }
}
