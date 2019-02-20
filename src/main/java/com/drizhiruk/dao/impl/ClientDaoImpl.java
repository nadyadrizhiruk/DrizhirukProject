package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientDaoImpl implements ClientDao {
    private Map<Long, Client> map = new HashMap<>();
    private static int generator = 0;
    private static ClientDao clientDao;

    private ClientDaoImpl() {

    }

    @Override
    public boolean saveClient(Client client) {
        client.setId(generator++);
        map.put(client.getId(), client);
        System.out.println("Saving client");
        return true;
    }

    @Override
    public Client findById(long id) {
        return map.get(id);
    }

    @Override
    public boolean removeClient(long id) {
        if (map.containsKey(id)) {
            map.remove(id);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<Client> getListOfAllClients() {
        return new ArrayList<>(map.values());
    }

    public static ClientDao getInstance() {
        if (clientDao == null) {
            synchronized (ClientDaoImpl.class) {
                if (clientDao == null) {
                    clientDao = new ClientDaoImpl();
                }
            }
        }
        return clientDao;
    }
}
