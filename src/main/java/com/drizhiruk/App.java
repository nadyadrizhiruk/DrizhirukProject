package com.drizhiruk;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.dao.impl.ClientDaoImpl;
import com.drizhiruk.dao.impl.OrderDaoImpl;
import com.drizhiruk.dao.impl.ProductDaoImpl;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.clientInput.ClientService;
import com.drizhiruk.services.clientInput.ClientServiceImpl;
import com.drizhiruk.services.order_input.OrderService;
import com.drizhiruk.services.order_input.OrderServiceImpl;
import com.drizhiruk.services.product_input.ProductService;
import com.drizhiruk.services.product_input.ProductServiceImpl;
import com.drizhiruk.validators.ValidationService;
import com.drizhiruk.validators.impl.ValidationServiceImpl;
import com.drizhiruk.view.menu.client.ClientMenu;
import com.drizhiruk.view.menu.MainMenu;
import com.drizhiruk.view.menu.admin.AdminMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) throws IOException, BisnessException {

//        ClientDao clientDao = new ClientDaoImpl();
        ClientDao clientDao = ClientDaoImpl.getInstance();
        ValidationService validationService = new ValidationServiceImpl();

        ClientService clientService = new ClientServiceImpl(clientDao, validationService);

        OrderDao orderDao = new OrderDaoImpl();

        OrderService orderService = new OrderServiceImpl(orderDao, validationService);

        ProductDao productDao = new ProductDaoImpl();

        ProductService productService = new ProductServiceImpl(productDao, validationService);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        AdminMenu adminMenu = new AdminMenu(clientService, orderService, productService);
        ClientMenu clientMenu = new ClientMenu(br, clientService, orderService, productService);
        MainMenu mainMenu = new MainMenu(br, adminMenu, clientMenu);

        mainMenu.start();
    }
}