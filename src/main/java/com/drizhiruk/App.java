package com.drizhiruk;

import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.view.menu.MainMenu;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, BisnessException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app/app.xml");

        MainMenu mainMenu = (MainMenu) context.getBean(MainMenu.class);
        mainMenu.start();

        context.close();
    }

}
