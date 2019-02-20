package com.drizhiruk.validators;

import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;

import java.util.List;

public interface ValidationService {
    void validateAge(int age) throws BisnessException;
    void validateEmail(String email) throws BisnessException;
    void validatePhone(String phone) throws BisnessException;
    void checkExistence(List<Client> clientList, String phone) throws BisnessException;
}
