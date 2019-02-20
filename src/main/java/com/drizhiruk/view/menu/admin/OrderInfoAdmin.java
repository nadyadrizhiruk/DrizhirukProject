package com.drizhiruk.view.menu.admin;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.services.clientInput.ClientService;
import com.drizhiruk.services.order_input.OrderService;
import com.drizhiruk.services.product_input.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

class OrderInfoAdmin {

    private final BufferedReader br;

    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderInfoAdmin(BufferedReader br, ClientService clientService, OrderService orderService, ProductService productService){
        this.br = br;
        this.clientService = clientService;
        this.orderService = orderService;
        this.productService = productService;
    }

    public void show() throws IOException {

        boolean isRunning = true;

        while (isRunning) {

            ShowMenu();

            switch (br.readLine()) {
                case "1":
                    modifyOrder();
                    break;
                case "2":
                    removeOrder();
                    break;
                case "3":
                    printOrder(findOrder());
                    break;
                case "4":
                    listAllOrdersByClient();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }

    }

    private void ShowMenu() {
        System.out.println("1. Modify order");
        System.out.println("2. Close order");
        System.out.println("3. Print order");
        System.out.println("4. List all orders by customer");
        System.out.println("9. Return");
        System.out.println("0.Exit");
    }

    private void modifyOrder() throws IOException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());
        Order order = orderService.findById(id);
        if (order == null) {
            System.out.println("Wrong id");
            return;
        }

        boolean isRunning = true;
        boolean somethingWasChanged = false;
        Client client = order.getClient();
        String date = order.toString();
        List<Product> products = order.getProducts();

        while (isRunning) {

            System.out.println("Modifying:");
            System.out.println("1. client. Previous: " + client);
            System.out.println("2. date. Previous: " + date);
            System.out.println("3. products.");
            System.out.println("9. Return");

            switch (br.readLine()) {
                case "1":
                    client = findClient();
                    somethingWasChanged = true;
                    break;
                case "2":
                    date = br.readLine();
                    somethingWasChanged = true;
                    break;
                case "3":
                    products = modifyProductsList(products);
                    somethingWasChanged = true;
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
        if (somethingWasChanged) {
            orderService.modifyOrder(order, client, date, products);
        }
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

    private void removeOrder() throws IOException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());

        if (orderService.removeOrder(id)) {
            System.out.println("Successful attempt");
        } else {
            System.out.println("Unsuccessful attempt");
        }
    }

    private void printOrder(Order order) throws IOException {

        System.out.println("id: "+order.getId());
        System.out.println("date: "+order.getDate());
        System.out.println("client: "+order.getClient());
        for(Product product:order.getProducts()){
            System.out.println(product.getName());
        }
    }

    private void listAllOrdersByClient() throws IOException {

        Client client = findClient();
        List<Order> ordersList = orderService.findAllOrdersByClient(client);
        for (Order order:ordersList){
            printOrder(order);
        }

    }
}
