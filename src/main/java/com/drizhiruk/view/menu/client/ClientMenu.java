package com.drizhiruk.view.menu.client;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.services.clientInput.ClientService;
import com.drizhiruk.services.order_input.OrderService;
import com.drizhiruk.services.product_input.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClientMenu {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    public ClientMenu(ClientService clientService, OrderService orderService, ProductService productService) {
        this.clientService = clientService;
        this.orderService = orderService;
        this.productService = productService;
    }

    public void clientMenu() throws IOException {

        boolean isRunning = true;

        while (isRunning) {

            showMenu();

            switch (br.readLine()) {
                case "1":
                    printListOfAllProducts();
                    break;
                case "2":
                    createOrder();
                    break;
                case "3":
                    printOrder(findOrder());
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

    private void showMenu() {
        System.out.println("1. Print all products");
        System.out.println("2. Create order");
        System.out.println("3. Print order");
        System.out.println("9. Return");
        System.out.println("0.Exit");
    }

    private void printListOfAllProducts() {

        for (Product element : productService.getListOfAllProducts()) {
            System.out.println(element.getName());
        }

    }

    private void createOrder() throws IOException {

        Client client = findClient();
        String date = br.readLine();

        Order order = orderService.createOrder(client, date, new ArrayList<>());

        List<Product> products = modifyProductsList(order.getProducts());

        orderService.modifyOrder(order, client, date, products);

    }

    private List<Product> modifyProductsList(List<Product> products) throws IOException {

        boolean isRunning = true;

        Product product;

        while (isRunning) {

            System.out.println("Modifying:");
            System.out.println("1. remove element");
            System.out.println("2. add element");
            System.out.println("9. Return");

            switch (br.readLine()) {
                case "1":
                    product = findProduct();
                    products = orderService.deleteElementFromProductList(products, product);
                    break;
                case "2":
                    product = findProduct();
                    products = orderService.AddElementInProductList(products, product);
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
        return products;

    }

    private Client findClient() throws IOException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());
        Client client = clientService.findById(id);
        if (client == null) {
            System.out.println("Wrong id");
        }
        return client;
    }

    private Product findProduct() throws IOException {
        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());
        Product product = productService.findById(id);
        if (product == null) {
            System.out.println("Wrong id");
        }
        return product;
    }

    private Order findOrder() throws IOException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());
        Order order = orderService.findById(id);
        if (order == null) {
            System.out.println("Wrong id");
        }
        return order;
    }

    private void printOrder(Order order) throws IOException {

        System.out.println("id: "+order.getId());
        System.out.println("date: "+order.getDate());
        System.out.println("client: "+order.getClient());
        for(Product product:order.getProducts()){
            System.out.println(product.getName());
        }
    }
}


