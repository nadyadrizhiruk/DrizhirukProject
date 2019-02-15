package com.drizhiruk;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.dao.impl.ClientDaoImpl;
import com.drizhiruk.dao.impl.OrderDaoImpl;
import com.drizhiruk.dao.impl.ProductDaoImpl;
import com.drizhiruk.services.clientInput.ClientService;
import com.drizhiruk.services.clientInput.ClientServiceImpl;
import com.drizhiruk.services.order_input.OrderService;
import com.drizhiruk.services.order_input.OrderServiceImpl;
import com.drizhiruk.services.product_input.ProductService;
import com.drizhiruk.services.product_input.ProductServiceImpl;
import com.drizhiruk.view.menu.client.ClientMenu;
import com.drizhiruk.view.menu.MainMenu;
import com.drizhiruk.view.menu.admin.AdminMenu;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        ClientDao clientDao = new ClientDaoImpl();

        ClientService clientService = new ClientServiceImpl(clientDao);

        OrderDao orderDao = new OrderDaoImpl();

        OrderService orderService = new OrderServiceImpl(orderDao);

        ProductDao productDao = new ProductDaoImpl();

        ProductService productService = new ProductServiceImpl(productDao);

        AdminMenu adminMenu = new AdminMenu(clientService, orderService, productService);
        ClientMenu clientMenu = new ClientMenu(clientService, orderService, productService);
        MainMenu mainMenu = new MainMenu(adminMenu, clientMenu);

        mainMenu.start();
    }
}
