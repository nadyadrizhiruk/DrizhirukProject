package com.drizhiruk.services.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.validators.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private ValidationService validationService;

    @Autowired
    public ClientServiceImpl(ClientDao clDao, ValidationService validationService) {
        clientDao = clDao;
        this.validationService = validationService;
    }

    @Override
    public void createClient(String name, String surname, String phone, int age, String email) {

        try {
            validationService.validateAge(age);
            validationService.validateEmail(email);
            validationService.validatePhone(phone);
            validationService.checkExistence(phone);
            Client client = new Client(name, surname, age, email, phone);
            clientDao.saveClient(client);
        } catch (BisnessException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createClient(String name, String surname, String phone) {

        createClient(name, surname, phone, 0, "");
    }

    @Override
    public Client findById(long id) {

        return clientDao.findById(id);

    }

    @Override
    public void modifyClient(Client client, String name, String surname, int age, String email, String phone) {
        try {
            validationService.validateAge(age);
            validationService.validateEmail(email);
            validationService.validatePhone(phone);
            client.setName(name);
            client.setSurname(surname);
            client.setAge(age);
            client.setEmail(email);
            client.setPhone(phone);
            clientDao.saveExistingClient(client);
        } catch (BisnessException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean removeClient(long id) {

        return clientDao.removeClient(id);

    }

    @Override
    public List<Client> getListOfAllClients() {

        return clientDao.getListOfAllClients();
    }

}
