package com.drizhiruk.view.menu.client;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.services.ProductService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
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
                    Order order = findOrder();
                    if (order != null) {
                        printOrder(order);
                    }
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
        System.out.println("Input phone (000 000 00 00): ");
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
            System.out.println("9. Done");

            switch (br.readLine()) {
                case "1":
                    System.out.println("Input name");
                    name = br.readLine();
                    somethingWasChanged = true;
                    break;
                case "2":
                    System.out.println("Input surname");
                    surname = br.readLine();
                    somethingWasChanged = true;
                    break;
                case "3":
                    System.out.println("Input age");
                    age = readInteger();
                    somethingWasChanged = true;
                    break;
                case "4":
                    System.out.println("Input email");
                    email = br.readLine();
                    somethingWasChanged = true;
                    break;
                case "5":
                    System.out.println("Input phone");
                    phone = br.readLine();
                    somethingWasChanged = true;
                    break;
                case "9":
                    return;
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
        System.out.println("Input an order date");
        String date = br.readLine();

        Order order = orderService.createOrderObject(client, date, new ArrayList<ProductInOrder>());

        fillProductsList(order);

        orderService.saveNewOrder(order);
    }

    private void fillProductsList(Order order) throws IOException {

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

    private Client findClient() throws IOException {

        System.out.println("Input client id: ");
        long id = Long.parseLong(br.readLine());
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

    private Order findOrder() throws IOException {

        System.out.println("Input order id: ");
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
        for (ProductInOrder product : order.getProducts()) {
            System.out.println(product.getProduct().getName());
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

    private BigDecimal readBigDecimal(){
        try {
            return new BigDecimal(br.readLine());
        }
        catch(IOException|NumberFormatException ex){
            System.out.println("Input number please");
            return  readBigDecimal();
        }
    }
}


