package com.drizhiruk.view.menu.admin;

import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.services.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminMenu {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    public AdminMenu(ClientService clientService, OrderService orderService, ProductService productService) {
        this.clientService = clientService;
        this.orderService = orderService;
        this.productService = productService;
    }

    public void show() throws IOException, BisnessException {

        boolean isRunning = true;

        while (isRunning) {

            ShowMenu();

            switch (br.readLine()) {
                case "1":
                    ClientInfoAdmin clientInfoAdmin = new ClientInfoAdmin(br, clientService);
                    clientInfoAdmin.show();
                    break;
                case "2":
                    ProductInfoAdmin productInfoAdmin = new ProductInfoAdmin(br, productService, clientService);
                    productInfoAdmin.show();
                    break;
                case "3":
                    OrderInfoAdmin orderInfoAdmin = new OrderInfoAdmin(br, clientService, orderService, productService);
                    orderInfoAdmin.show();
                    break;
                case "0":
                    System.exit(0);
                    break;
                case "9":
                    System.out.println("Quit");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }

    }

    private void ShowMenu() {
        System.out.println("1. Admining Clients");
        System.out.println("2. Admining products");
        System.out.println("3. Admining orders");
        System.out.println("9. Return");
        System.out.println("0.Exit");
    }
}

