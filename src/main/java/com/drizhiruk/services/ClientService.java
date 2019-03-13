package com.drizhiruk.services;

import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;

import java.util.List;

public interface ClientService {

    /**
     * method create new object of Client and save it
     * @param name name of client
     * @param surname surname of client
     * @param phone phone of client
     */
    void createClient(String name, String surname, String phone, int age, String email) throws BisnessException;
    void createClient(String name, String surname, String phone);

    /**
     * method find and return client object  by id
     * @param id id of client
     * @return client by id or null
     */

    Client findById(long id);

    /**
     * method modify fields of client object
     * @param client client object
     * @param name new name of client
     * @param surname new surname of client
     * @param age new age of client
     * @param email new email of client
     * @param phone new phone of client
     */

    void modifyClient(Client client, String name, String surname, int age, String email, String phone) throws BisnessException;

    /**
     * save client
     * @param client client object
     */

    boolean removeClient(long id);

    /**
     * method for getting list of all clients
     * @return list of clients
     */

    List<Client> getListOfAllClients();
}
