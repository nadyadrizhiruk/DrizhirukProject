package com.drizhiruk.services.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.services.ProductService;
import com.drizhiruk.validators.ValidationService;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)

public class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    private ValidationService validationService = Mockito.mock(ValidationService.class);
    private OrderService orderService;

    @Before
    public void init() {

        orderService = new OrderServiceImpl(orderDao, validationService);

    }


    @Test

    public void findById_RightIdTest() {

        //GIVEN
        long id = 1;
        Order expectedOrder = new Order(id, new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28"), "sdfsdfds");

        Mockito.when(orderDao.findById(id)).thenReturn(expectedOrder);

        //WHEN
        Order order = orderService.findById(id);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(orderDao);
        Assert.assertEquals(expectedOrder, order);
    }

    @Test

    public void findById_WrongIdTest() {

        //GIVEN
        long id = -1;
        Order expectedOrder = null;

        Mockito.when(orderDao.findById(id)).thenReturn(expectedOrder);

        //WHEN
        Order order = orderService.findById(id);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(orderDao);
        Assert.assertEquals(expectedOrder, order);
    }

    @Test
    public void removeOrderTest() {

        //GIVEN
        long id = 1;
        boolean expectedResult = true;
        Mockito.when(orderDao.removeOrder(id)).thenReturn(true);

        //WHEN
        boolean result = orderService.removeOrder(id);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).removeOrder(id);
        Mockito.verifyNoMoreInteractions(orderDao);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void createOrderTest() throws BisnessException {

        //GIVEN
        List <ProductInOrder> productInOrders = new ArrayList<>();
        Client client = new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28");
        String date = "rrrrr";

        Mockito.when(orderDao.saveNewOrder((Order) any())).thenReturn(true);

        //WHEN
        orderService.createOrder(client, date, productInOrders);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).saveNewOrder((Order) any());
        Mockito.verifyNoMoreInteractions(orderDao);

    }

    @Test

    public void modifyOrderTest() throws BisnessException {

        //GIVEN
        long id = 1;
        Client client = new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28");
        String date = "rrrrr";

        Order expectedOrder = new Order(id,client, date);

        Mockito.when(orderDao.saveExistingOrder(expectedOrder)).thenReturn(true);

        expectedOrder.setClient(new Client("test1", "test1", "095 744 11 28"));
        expectedOrder.setDate("fffff");

        //WHEN
        orderService.modifyOrder(expectedOrder,client,date);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).saveExistingOrder(expectedOrder);
        Mockito.verifyNoMoreInteractions(orderDao);
        Assert.assertEquals(client, expectedOrder.getClient());
        Assert.assertEquals(date, expectedOrder.getDate());


    }

    @Test

    public void findAllOrdersByClientTest() {

        //GIVEN
        long id = 1;
        Client client = new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28");
        List<Order> expectedOrders = new ArrayList();
        expectedOrders.add(new Order(client, "rrrrr",new ArrayList<ProductInOrder>()));

        Mockito.when(orderDao.findAllOrdersByClient(client)).thenReturn(expectedOrders);

        //WHEN
        List <Order> orders = orderService.findAllOrdersByClient(client);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).findAllOrdersByClient(client);
        Mockito.verifyNoMoreInteractions(orderDao);
        Assert.assertEquals(expectedOrders, orders);
    }



    @After
    public void tearDawn() {

        orderService = null;

    }



}



