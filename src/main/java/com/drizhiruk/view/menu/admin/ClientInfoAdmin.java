package com.drizhiruk.view.menu.admin;

import com.drizhiruk.domain.Client;
import com.drizhiruk.services.clientInput.ClientService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ClientInfoAdmin {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final ClientService clientService;

    ClientInfoAdmin(ClientService clientService) {
        this.clientService = clientService;
    }

    public void show() throws IOException {

        boolean isRunning = true;

        while (isRunning) {

            ShowMenu();

            switch (br.readLine()) {
                case "1":
                    createClient();
                    break;
                case "2":
                    modifyClient();
                    break;
                case "3":
                    removeClient();
                    break;
                case "4":
                    printClientInfo();
                    break;
                case "5":
                    prinListOfAllClients();
                    break;
                case "9":
                    System.out.println("Quit");
                    isRunning = false;
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
        System.out.println("1. Add client");
        System.out.println("2. Modify client");
        System.out.println("3. Remove client");
        System.out.println("4. Print client info");
        System.out.println("5. List all clients");
        System.out.println("9. Return");
        System.out.println("0.Exit");
    }

    private void createClient() throws IOException {
        System.out.println("Input name: ");
        String name = br.readLine();
        System.out.println("Input surname: ");
        String surname = br.readLine();
        System.out.println("Input phone: ");
        String phone = br.readLine();
        clientService.createClient(name, surname, phone);
    }

    private void modifyClient() throws IOException {

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
                    name = br.readLine();
                    somethingWasChanged=true;
                    break;
                case "2":
                    surname = br.readLine();
                    somethingWasChanged=true;
                    break;
                case "3":
                    age = Integer.parseInt(br.readLine());
                    somethingWasChanged=true;
                    break;
                case "4":
                    email = br.readLine();
                    somethingWasChanged=true;
                    break;
                case "5":
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

    private void removeClient() throws IOException {

        System.out.println("Input id: ");
        long id = Long.parseLong(br.readLine());

        if (clientService.removeClient(id)) {
            System.out.println("Successful attempt");
        } else {
            System.out.println("Unsuccessful attempt");
        }
    }

    private void printClientInfo() throws IOException {

        Client client = findClient();
        System.out.println(client);

    }

    private void prinListOfAllClients() {

        for (Client element : clientService.getListOfAllClients()) {
            System.out.println(element.getName());
        }

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

}
