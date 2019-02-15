package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

//    private final List<Client> clients = new ArrayList<>();

    @Override
    public boolean saveClient(Client client) {
        System.out.println("Saving client");
        return true;
    }

    @Override
    public Client findById(long id) {
        return new Client("Check","","");
    }

    @Override
    public boolean removeClient(long id) {
        System.out.println("client removing");
        return true;
    }

    @Override
    public List<Client> getListOfAllClients() {
        return new ArrayList<>();
    }


}
