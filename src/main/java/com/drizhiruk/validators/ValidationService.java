package com.drizhiruk.validators;

import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;

import java.util.List;

public interface ValidationService {
    void validateAge(int age) throws BisnessException;
    void validateEmail(String email) throws BisnessException;
    void validatePhone(String phone) throws BisnessException;
    void checkExistence(String phone) throws BisnessException;
    void checkExistenceClientId(long id) throws BisnessException;
    void checkExistenceOrderId(long id) throws BisnessException;
    void checkExistenceProductId(long id) throws BisnessException;
}
