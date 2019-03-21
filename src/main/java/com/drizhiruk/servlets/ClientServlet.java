package com.drizhiruk.servlets;

import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


public class ClientServlet extends HttpServlet {

    private ClientService clientService;

    public ClientServlet(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String age = req.getParameter("age");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        try {
            clientService.createClient(name, surname, phone, Integer.parseInt(age), email);
        } catch (BisnessException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String age = req.getParameter("age");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        Client client = clientService.findById(Long.parseLong(id));
        try {
            clientService.modifyClient(client, name, surname, Integer.parseInt(age),email, phone);
        } catch (BisnessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String[]> parameters = req.getParameterMap();
        if (parameters.containsKey("id")) {
            PrintWriter writer = resp.getWriter();
            for (String id : parameters.get("id")) {
                Client client = clientService.findById(Long.parseLong(id));
                writer.println("<h1>" + client + "</h1>");
                writer.println("<br>");
            }
        } else {
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            for (Client client : clientService.getListOfAllClients()) {
                writer.println("<h1>" + client + "</h1>");
                writer.println("<br>");

            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        clientService.removeClient(id);
    }
}
