package com.drizhiruk.view.menu.client;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.clientInput.ClientService;
import com.drizhiruk.services.order_input.OrderService;
import com.drizhiruk.services.product_input.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientMenu {

    private final BufferedReader br;
    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    public ClientMenu(BufferedReader br, ClientService clientService, OrderService orderService, ProductService productService) {
        this.br = br;
        this.clientService = clientService;
        this.orderService = orderService;
        this.productService = productService;
    }

    public void clientMenu() throws IOException, BisnessException {

        boolean isRunning = true;

        while (isRunning) {

            showMenu();

            switch (br.readLine()) {

                case "1":
                    createClient();
                    break;
                case "2":
                    modifyClient();
                    break;
                case "3":
                    printListOfAllProducts();
                    break;
                case "4":
                    createOrder();
                    break;
                case "5":
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
        System.out.println("1. Register");
        System.out.println("2. Modify");
        System.out.println("3. List products");
        System.out.println("4. Add order");
        System.out.println("5. Print order");
        System.out.println("9. Return");
        System.out.println("0.Exit");
    }

    private void createClient() throws IOException, BisnessException {
        System.out.println("Input name: ");
        String name = br.readLine();
        System.out.println("Input surname: ");
        String surname = br.readLine();
        System.out.println("Input age: ");
        int age = readInteger();
        System.out.println("Input phone: ");
        String phone = br.readLine();
        System.out.println("Input email: ");
        String email = br.readLine();
        clientService.createClient(name, surname, phone, age, email);
    }

    private void modifyClient() throws IOException, BisnessException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());
        Client client = clientService.findById(id);
        if (client == null) {
            System.out.println("Wrong id");
            return;
        }

        boolean isRunning = true;
        boolean somethingWasChanged = false;
        String name = client.getName();
        String surname = client.getSurname();
        int age = client.getAge();
        String email = client.getEmail();
        String phone = client.getPhone();

        while (isRunning) {

            System.out.println("Modifying:");
            System.out.println("1. Name. Previous: " + name);
            System.out.println("2. Surname. Previous: " + surname);
            System.out.println("3. Age. Previous: " + age);
            System.out.println("4. Email. Previous: " + email);
            System.out.println("5. Phone. Previous: " + phone);
            System.out.println("9. Return");

            switch (br.readLine()) {
                case "1":
                    System.out.println("Input name");
                    name = br.readLine();
                    somethingWasChanged=true;
                    break;
                case "2":
                    System.out.println("Input surname");
                    surname = br.readLine();
                    somethingWasChanged=true;
                    break;
                case "3":
                    System.out.println("Input age");
                    age = readInteger();
                    somethingWasChanged=true;
                    break;
                case "4":
                    System.out.println("Input email");
                    email = br.readLine();
                    somethingWasChanged=true;
                    break;
                case "5":
                    System.out.println("Input phone");
                    phone = br.readLine();
                    somethingWasChanged=true;
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
            clientService.modifyClient(client, name, surname, age, email, phone);
        }
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

        System.out.println("id: " + order.getId());
        System.out.println("date: " + order.getDate());
        System.out.println("client: " + order.getClient());
        for (Product product : order.getProducts()) {
            System.out.println(product.getName());
        }
    }

    private int readInteger(){
        try {
            return Integer.parseInt(br.readLine());
        }
        catch(IOException|NumberFormatException ex){
            System.out.println("Input number please");
            return  readInteger();
        }
    }
}


