package com.drizhiruk.view.menu.admin;

import com.drizhiruk.domain.Product;
import com.drizhiruk.services.product_input.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

class ProductInfoAdmin {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private final ProductService productService;

    ProductInfoAdmin(ProductService productService) {
        this.productService = productService;
    }

    public void show() throws IOException {

        boolean isRunning = true;

        while (isRunning) {

            ShowMenu();

            switch (br.readLine()) {
                case "1":
                    createProduct();
                    break;
                case "2":
                    modifyProduct();
                    break;
                case "3":
                    removeProduct();
                    break;
                case "4":
                    printProductInfo();
                    break;
                case "5":
                    printListOfAllProducts();
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
        System.out.println("1. Add product");
        System.out.println("2. Modify product");
        System.out.println("3. Remove product");
        System.out.println("4. Print product info");
        System.out.println("5. List all products");
        System.out.println("9. Return");
        System.out.println("0.Exit");
    }

    private void createProduct() throws IOException {
        System.out.println("Input name: ");
        String name = br.readLine();
        System.out.println("Input prise: ");
        BigDecimal price = new BigDecimal(br.readLine());
        System.out.println("Input phone: ");
        int amount = Integer.parseInt(br.readLine());
        productService.createProduct(name, price, amount);
    }

    private void modifyProduct() throws IOException {
        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());
        Product product = productService.findById(id);
        if (product == null) {
            System.out.println("Wrong id");
            return;
        }

        boolean isRunning = true;
        boolean somethingWasChanged = false;
        String name = product.getName();
        BigDecimal price = product.getPrice();
        int amount = product.getAmount();

        while (isRunning) {

            System.out.println("Modifying:");
            System.out.println("1. Name. Previous: " + name);
            System.out.println("2. price. Previous: " + price);
            System.out.println("3. amount. Previous: " + amount);
            System.out.println("9. Return");

            switch (br.readLine()) {
                case "1":
                    name = br.readLine();
                    somethingWasChanged=true;
                    break;
                case "2":
                    price = new BigDecimal(br.readLine());
                    somethingWasChanged=true;
                    break;
                case "3":
                    amount = Integer.parseInt(br.readLine());
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
            productService.modifyProduct(product, name, price, amount);
        }
    }

    private void removeProduct() throws IOException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());

        if (productService.removeProduct(id)) {
            System.out.println("Successful attempt");
        } else {
            System.out.println("Unsuccessful attempt");
        }
    }

    private void printProductInfo() throws IOException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());
        Product client = productService.findById(id);
        if (client == null) {
            System.out.println("Wrong id");
            return;
        }
        System.out.println(client);

    }

    private void printListOfAllProducts() {

        for (Product element : productService.getListOfAllProducts()) {
            System.out.println(element.getName());
        }

    }



}
