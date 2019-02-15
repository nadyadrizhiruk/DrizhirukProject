package com.drizhiruk.services.clientInput;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clDao) {
        clientDao = clDao;
    }

    /**
     * method create new object of Client and save it
     * @param name name of client
     * @param surname surname of client
     * @param phone phone of client
     */
    @Override
    public void createClient(String name, String surname, String phone) {

        Client client = new Client(name, surname, phone);
        saveClient(client);
    }

    /**
     * method find and return client object  by id
     * @param id id of client
     * @return client by id or null
     */
    @Override
    public Client findById(long id) {

        return clientDao.findById(id);

    }

    /**
     * method modify fields of client object
     * @param client client object
     * @param name new name of client
     * @param surname new surname of client
     * @param age new age of client
     * @param email new email of client
     * @param phone new phone of client
     */

    @Override
    public void modifyClient(Client client, String name, String surname, int age, String email, String phone) {
        client.setName(name);
        client.setSurname(surname);
        client.setAge(age);
        client.setEmail(email);
        client.setPhone(phone);
        saveClient(client);
    }

    /**
     * save client
     * @param client client object
     */
    @Override
    public void saveClient(Client client) {
        boolean result = clientDao.saveClient(client);
        if (result) {
            System.out.println("Client " + client + "saved");
        }
    }

    /**
     * method remove client
     * @param id client id to find client for removing
     * @return successful (true) or unsuccessful(false) result of removing
     */
    @Override
    public boolean removeClient(long id) {

        return clientDao.removeClient(id);

    }

    /**
     * method for getting list of all clients
     * @return list of clients
     */
    @Override
    public List<Client> getListOfAllClients() {

        return clientDao.getListOfAllClients();
    }

}
