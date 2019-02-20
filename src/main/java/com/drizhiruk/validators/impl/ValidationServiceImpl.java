package com.drizhiruk.validators.impl;

import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.validators.ValidationService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateAge(int age) throws BisnessException {

        if ((age < 0) || (age > 150)) {
            throw new BisnessException("Incorrect age");
        }

    }

    @Override
    public void validateEmail(String  email) throws BisnessException {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_.-]{2,20}[@][a-zA-Z0-9_-]{1,8}[//.][a-zA-Z0-9_-]{1,5}$");
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new BisnessException("Incorrect email");
        }
    }

    @Override
    public void validatePhone(String phone) throws BisnessException {
        Pattern p = Pattern.compile("^(067|097|050|095)[ ][0-9]{3}[ ][0-9]{2}[ ][0-9]{2}$");
        Matcher m = p.matcher(phone);
        if (!m.matches()) {
            throw new BisnessException("Incorrect phone");
        }
    }

    @Override
    public void checkExistence(List<Client> clientList, String phone) throws BisnessException {

        for (Client client: clientList){
            if(client.getPhone().equals(phone)){
                throw new BisnessException("The client with the same number is already exists");
            }
        }

    }
}
