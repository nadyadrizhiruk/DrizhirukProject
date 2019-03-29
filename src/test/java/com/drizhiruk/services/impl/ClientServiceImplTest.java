package com.drizhiruk.services.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.validators.ValidationService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)

public class ClientServiceImplTest {

    @Mock
    private ClientDao clientDao;

    private ValidationService validationService = Mockito.mock(ValidationService.class);
    private ClientService clientService;

    @Before
    public void init() {

        clientService = new ClientServiceImpl(clientDao, validationService);

    }


    @Test

    public void findById_RightIdTest() {

        //GIVEN
        long id = 1;
        Client expectedClient = new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28");

        Mockito.when(clientDao.findById(id)).thenReturn(expectedClient);

        //WHEN
        Client client = clientService.findById(id);

        //Then
        Mockito.verify(clientDao, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(clientDao);
        Assert.assertEquals(expectedClient, client);
    }

    @Test

    public void findById_WrongIdTest() {

        //GIVEN
        long id = -1;
        Client expectedClient = null;
        Mockito.when(clientDao.findById(id)).thenReturn(null);

        //WHEN
        Client client = clientService.findById(id);

        //Then
        Mockito.verify(clientDao, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(clientDao);
        Assert.assertEquals(expectedClient, client);
    }

    @Test
    public void removeClientTest() {

        //GIVEN
        long id = 1;
        boolean expectedResult = true;
        Mockito.when(clientDao.removeClient(id)).thenReturn(true);

        //WHEN
        boolean result = clientService.removeClient(id);

        //Then
        Mockito.verify(clientDao, Mockito.times(1)).removeClient(1);
        Mockito.verifyNoMoreInteractions(clientDao);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void createClientTest() throws BisnessException {

        //GIVEN
        String name = "test";
        String surname = "test";
        int age = 10;
        String phone = "095 744 11 28";
        String email = "ghfg@bgf.fg";

        Mockito.when(clientDao.saveClient((Client) any())).thenReturn(true);
//        Mockito.doNothing().when(clientService).saveClient(clientToSave);

        //WHEN
        clientService.createClient(name, surname, phone, age, email);

        //Then
        Mockito.verify(clientDao, Mockito.times(1)).saveClient((Client) any());
        Mockito.verifyNoMoreInteractions(clientDao);

    }

    @Test

    public void modifyClientTest() throws BisnessException {

        //GIVEN
        long id = 1;
        String name = "test1";
        String surname = "test1";
        int age = 10;
        String phone = "095 744 11 28";
        String email = "ghfg@bgf.fg";
        Client expectedClient = new Client(id,name, surname, age, email, phone);

        Mockito.when(clientDao.saveExistingClient(expectedClient)).thenReturn(true);

        expectedClient.setName("test");
        expectedClient.setSurname("test");
        expectedClient.setAge(9);
        expectedClient.setPhone("095 744 11 27");
        expectedClient.setEmail("ghfg@bgf.fe");
        //WHEN

        clientService.modifyClient(expectedClient,name,surname,age,email,phone);

        //Then
        Mockito.verify(clientDao, Mockito.times(1)).saveExistingClient(expectedClient);
        Mockito.verifyNoMoreInteractions(clientDao);
        Assert.assertEquals(name, expectedClient.getName());
        Assert.assertEquals(surname, expectedClient.getSurname());
        Assert.assertEquals(age, expectedClient.getAge());
        Assert.assertEquals(phone, expectedClient.getPhone());
        Assert.assertEquals(email, expectedClient.getEmail());
    }

    @Test

    public void getListOfAllClientsTest() {

        //GIVEN
        long id = 1;
        List expectedclients = new ArrayList();
        expectedclients.add(new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28"));

        Mockito.when(clientDao.getListOfAllClients()).thenReturn(expectedclients);

        //WHEN
        List clients = clientService.getListOfAllClients();

        //Then
        Mockito.verify(clientDao, Mockito.times(1)).getListOfAllClients();
        Mockito.verifyNoMoreInteractions(clientDao);
        Assert.assertEquals(clients, expectedclients);
    }



    @After
    public void tearDawn() {

        clientService = null;

    }



}



