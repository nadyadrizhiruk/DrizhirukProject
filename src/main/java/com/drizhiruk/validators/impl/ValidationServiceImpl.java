package com.drizhiruk.validators.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Product;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.validators.ValidationService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationServiceImpl implements ValidationService {

    private final ClientDao clientDao;
    private final ProductDao productDao;
    private final OrderDao orderDao;
    private final String emailPattern = "^[a-zA-Z0-9_.-]{2,20}[@][a-zA-Z0-9_-]{1,8}[//.][a-zA-Z0-9_-]{1,5}$";
    private final String phonePattern = "^(067|097|050|095)[ ][0-9]{3}[ ][0-9]{2}[ ][0-9]{2}$";

    public ValidationServiceImpl(ClientDao clientDao, ProductDao productDao, OrderDao orderDao) {
        this.clientDao = clientDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    @Override
    public void validateAge(int age) throws BisnessException {

        if ((age < 0) || (age > 150)) {
            throw new BisnessException("Incorrect age");
        }

    }

    @Override
    public void validateEmail(String email) throws BisnessException {
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new BisnessException("Incorrect email");
        }
    }

    @Override
    public void validatePhone(String phone) throws BisnessException {
        Pattern p = Pattern.compile(phonePattern);
        Matcher m = p.matcher(phone);
        if (!m.matches()) {
            throw new BisnessException("Incorrect phone");
        }
    }

    @Override
    public void checkExistence(String phone) throws BisnessException {


        for (Client client : clientDao.getListOfAllClients()) {
            if (client.getPhone().equals(phone)) {
                throw new BisnessException("The client with the same number is already exists");
            }
        }

    }

    @Override
    public void checkExistenceClientId(long id) throws BisnessException {

        Client client = clientDao.findById(id);
        if (client==null){
            throw new BisnessException("Wrong id");
        }

    }

    @Override
    public void checkExistenceProductId(long id) throws BisnessException {

        if (productDao.findById(id)==null){
            throw new BisnessException("Wrong id");
        }

    }

    @Override
    public void checkExistenceOrderId(long id) throws BisnessException {

        if (orderDao.findById(id)==null){
            throw new BisnessException("Wrong id");
        }

    }
}
