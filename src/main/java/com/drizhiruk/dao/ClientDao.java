package com.drizhiruk.dao;

import com.drizhiruk.domain.Client;

import java.util.List;

public interface ClientDao {

    boolean saveClient(Client client);
    Client findById(long id);
    boolean removeClient(long id);
    List<Client> getListOfAllClients();
}
