package com.drizhiruk.controllers;

import com.drizhiruk.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/mainMenu")
    public String showAllClients2(ModelMap modelMap){

        return "mainMenu";
    }
}