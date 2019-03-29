package com.drizhiruk.view.menu.admin;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
class OrderInfoAdmin {

    private final BufferedReader br;

    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderInfoAdmin(BufferedReader br, ClientService clientService, OrderService orderService, ProductService productService) {
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
                case "9":
                    return;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }

    }

    private void ShowMenu() {
        System.out.println("1. Modify order");
        System.out.println("2. Remove order");
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
        String date = order.getDate();

        while (isRunning) {

            System.out.println("Modifying:");
            System.out.println("1. client. Previous: " + client);
            System.out.println("2. date. Previous: " + date);
            System.out.println("3. products.");
            System.out.println("9. Done");

            switch (br.readLine()) {
                case "1":
                    System.out.println("Input client: ");
                    client = findClient();
                    somethingWasChanged = true;
                    break;
                case "2":
                    System.out.println("Input date: ");
                    date = br.readLine();
                    somethingWasChanged = true;
                    break;
                case "3":
                    modifyProductsList(order);
                    somethingWasChanged = true;
                    break;
                case "9":
                    if (somethingWasChanged) {

                        orderService.modifyOrder(order, client, date);
                    }
                    return;
                default:
                    System.out.println("Wrong input");
                    break;
            }

        }

    }

    private void modifyProductsList(Order order) throws IOException {

        boolean isRunning = true;

        Product product;

        while (isRunning) {

            System.out.println("Modifying products:");
            System.out.println("1. remove element");
            System.out.println("2. add element");
            System.out.println("9. Done");

            switch (br.readLine()) {
                case "1":
                    removeProductInOrder(order);
                    break;
                case "2":
                    addProductInOrder(order);
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

    private void removeProductInOrder(Order order) throws IOException {

        System.out.println("Input product in order id: ");
        long id = Long.parseLong(br.readLine());
        if (!orderService.removeProductInOrderById(order, id)) {
            System.out.println("Wrong id");
        }
    }

    private void addProductInOrder(Order order) throws IOException {
        Product product = findProduct();
        if (product != null) {
            System.out.println("Input prise: ");
            BigDecimal price = readBigDecimal();
            System.out.println("Input amount: ");
            int amount = readInteger();

            ProductInOrder productInOrder = orderService.createProductInOrderObject(product,price,amount,order);

            orderService.AddElementInProductList(order, productInOrder);
        }
    }

    private Client findClient() throws IOException {

        System.out.println("Input client id: ");
        long id = readLong();
        Client client = clientService.findById(id);
        if (client == null) {
            System.out.println("Wrong id");
        }
        return client;
    }

    private Product findProduct() throws IOException {
        System.out.println("Input product id: ");
        long id = Long.parseLong(br.readLine());
        Product product = productService.findById(id);
        if (product == null) {
            System.out.println("Wrong id");
        }
        return product;
    }

    private Order findOrder() throws IOException {

        System.out.println("Input order id: ");
        long id = readLong();
        Order order = orderService.findById(id);
        if (order == null) {
            System.out.println("Wrong id");
        }
        return order;
    }

    private void removeOrder() throws IOException {

        System.out.println("Input order id: ");
        long id = readLong();

        if (orderService.removeOrder(id)) {
            System.out.println("Successful attempt");
        } else {
            System.out.println("Unsuccessful attempt");
        }
    }

    private void printOrder(Order order) throws IOException {

        if (order != null) {
            System.out.println("id: " + order.getId());
            System.out.println("date: " + order.getDate());
            System.out.println("client: " + order.getClient());
            for (ProductInOrder product : order.getProducts()) {
                System.out.println(product.getProduct().getName());
            }
        }
    }

    private void listAllOrdersByClient() throws IOException {

        Client client = findClient();
        List<Order> ordersList = orderService.findAllOrdersByClient(client);
        for (Order order : ordersList) {
            printOrder(order);
        }

    }

    private long readLong() {
        try {
            return Long.parseLong(br.readLine());
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Input number please");
            return readLong();
        }
    }

    private BigDecimal readBigDecimal(){
        try {
            return new BigDecimal(br.readLine());
        }
        catch(IOException|NumberFormatException ex){
            System.out.println("Input number please");
            return  readBigDecimal();
        }
    }

    private int readInteger() {
        try {
            return Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Input number please");
            return readInteger();
        }
    }

//    private LocalDateTime readDate() {
//        try {
//            return LocalDateTime.parse(br.readLine());
//        } catch (IOException | NumberFormatException ex) {
//            System.out.println("Input date in format yyyy-MM-dd-HH-mm-ss.zzz please");
//            return readDate();
//        }
//    }
}
