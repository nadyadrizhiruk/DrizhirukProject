package com.drizhiruk.services.clientInput;

import com.drizhiruk.domain.Client;

import java.util.List;

public interface ClientService {

    void createClient(String name, String surname, String phone);
    Client findById(long id);
    void modifyClient(Client client, String name, String surname, int age, String email, String phone);
    void saveClient(Client client);
    boolean removeClient(long id);
    List<Client> getListOfAllClients();
}
