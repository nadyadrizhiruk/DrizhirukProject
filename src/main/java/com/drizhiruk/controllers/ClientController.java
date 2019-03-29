package com.drizhiruk.controllers;

import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/clients")
    public String showAllClients(ModelMap modelMap){
        modelMap.put("message", clientService.getListOfAllClients());
        return "printClients";
    }


    @RequestMapping(value="/clients", method= RequestMethod.POST)
    public String addClient(@RequestParam(required = false) String kindOfMethod,
                            @RequestParam(required = false) Long id,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String surname,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) String phone,
                            @RequestParam(required = false) Integer age) {
        if ("put".equals(kindOfMethod)) {
            return modifyClient(id,name,surname,email,phone,age);
        } else if ("delete".equals(kindOfMethod)) {
            return deleteClient(id);
        }

        try {
            clientService.createClient(name, surname, phone, age, email);
        } catch (BisnessException e) {
            e.printStackTrace();
        }
        return "clientsMenu";
    }

    @RequestMapping(value="/clients", method= RequestMethod.PUT)
    public String modifyClient(@RequestParam(value="id") Long id,
                               @RequestParam(value="name") String name,
                               @RequestParam(value="surname") String surname,
                               @RequestParam(value="email") String email,
                               @RequestParam(value="phone") String phone,
                               @RequestParam(value="age") Integer age) {
        try {
            clientService.modifyClient(clientService.findById(id), name, surname, age, email, phone) ;
        } catch (BisnessException e) {
            e.printStackTrace();
        }
        return "clientsMenu";
    }

    @RequestMapping(value="/clients", method= RequestMethod.DELETE)
    public String deleteClient(@RequestParam(value="id") Long id) {
        clientService.removeClient(id);
        return "clientsMenu";
    }

    @GetMapping(value = "/clientsMenu")
    public String showClientMenu(){
        return "clientsMenu";
    }


    @RequestMapping(value = "/add-client", method=RequestMethod.GET)
    public String addClientPage() {
        return "addClient";
    }

    @RequestMapping(value = "/modify-client", method=RequestMethod.GET)
    public String modifyClientPage() {
        return "modifyClient";
    }

    @RequestMapping(value = "/delete-client", method=RequestMethod.GET)
    public String deleteClientPage() {
        return "deleteClient";
    }
}