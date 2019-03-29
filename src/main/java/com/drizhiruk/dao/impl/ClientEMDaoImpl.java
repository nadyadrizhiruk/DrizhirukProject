package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Component
public class ClientEMDaoImpl implements ClientDao {

//    private EntityManager entityManager;
    private EntityManagerFactory factory;

    @Autowired
    public ClientEMDaoImpl() {
        factory = Persistence.createEntityManagerFactory("persistence-unit");
//        this.entityManager = factory.createEntityManager();
    }

    @Override
    public boolean saveClient(Client client) {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Client ClientFromDB = entityManager.merge(client);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public boolean saveExistingClient(Client client) {
        saveClient(client);
        return false;
    }

    @Override
    public Client findById(long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(Client.class, id);
    }

    @Override
    public boolean removeClient(long id) {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Client WHERE id=" + id).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public List<Client> getListOfAllClients() {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Client> clients = entityManager.createQuery("from Client", Client.class).getResultList();
        return clients;
    }
}
