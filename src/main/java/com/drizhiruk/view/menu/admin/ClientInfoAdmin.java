package com.drizhiruk.view.menu.admin;

import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.clientInput.ClientService;

import java.io.BufferedReader;
import java.io.IOException;

class ClientInfoAdmin {

    private final BufferedReader br;
    private final ClientService clientService;

    ClientInfoAdmin(BufferedReader br, ClientService clientService) {
        this.br = br;
        this.clientService = clientService;
    }

    public void show() throws IOException, BisnessException {

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

        System.out.println("List of clients: ");
        for (Client element : clientService.getListOfAllClients()) {
            System.out.println(element.getSurname()+" "+element.getName()+" id: "+element.getId());
        }
        System.out.println();
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
