package com.drizhiruk.view.menu;

import com.drizhiruk.view.menu.admin.AdminMenu;
import com.drizhiruk.view.menu.client.ClientMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenu {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private final AdminMenu adminMenu;
    private final ClientMenu clientMenu;

    public MainMenu(AdminMenu adminMenu, ClientMenu clientMenu) {
        this.adminMenu = adminMenu;
        this.clientMenu = clientMenu;
    }

    public void start() throws IOException {

        boolean isRunning = true;

        while (isRunning) {

            showMenu();

            switch (br.readLine()) {
                case "1":
                    adminMenu.show();
                    break;
                case "2":
                    clientMenu.clientMenu();
                    break;

                case "0":
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
        System.out.println("1. Admin");
        System.out.println("2. Client");
        System.out.println("0. Exit");
    }
}
