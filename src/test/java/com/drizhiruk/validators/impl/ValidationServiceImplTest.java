package com.drizhiruk.validators.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.validators.ValidationService;
import com.sun.deploy.util.SessionState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)

public class ValidationServiceImplTest {

    private ValidationService validationService;

    @Mock
    private ClientDao clientDao;

    @Before
    public void setUp() {

        validationService = new ValidationServiceImpl(clientDao);
    }

    @Test
    public void testValidateNormalAgeTest() throws BisnessException {

        //GIVEN
        int age = 50;

        //WHEN
        validationService.validateAge(age);
    }

    @Test(expected = BisnessException.class)

    public void validateTooLittleAge() throws BisnessException {

        //GIVEN
        int age = -1;

        //WHEN
        validationService.validateAge(age);

    }

    @Test(expected = BisnessException.class)

    public void validateToOldAge() throws BisnessException {

        //GIVEN
        int age = 300;

        //WHEN
        validationService.validateAge(age);

    }

    @Test
    public void testValidateCorrectEmail() throws BisnessException {

        //GIVEN
        String email = "tugvf@jhg.jh";

        //WHEN
        validationService.validateEmail(email);
    }

    @Test(expected = BisnessException.class)
    public void testValidateNonCorrectEmailDot() throws BisnessException {

        //GIVEN
        String email = "tugvf@jhgjh";

        //WHEN
        validationService.validateEmail(email);
    }

    @Test(expected = BisnessException.class)
    public void testValidateNonCorrectEmailAt() throws BisnessException {

        //GIVEN
        String email = "tugvfjhg.jh";

        //WHEN
        validationService.validateEmail(email);
    }

    @Test
    public void testvalidateCorrectPhone() throws BisnessException {

        //GIVEN
        String phone = "095 744 11 28";

        //WHEN
        validationService.validatePhone(phone);
    }

    @Test(expected = BisnessException.class)
    public void testvalidateWrongPhone() throws BisnessException {

        //GIVEN
        String phone = "044 744 11 28";

        //WHEN
        validationService.validatePhone(phone);
    }

    @Test(expected = BisnessException.class)
    public void testcheckExistenceWithExistingNumber() throws BisnessException {

        //GIVEN
        long id = 1;
        List expectedclients = new ArrayList();
        expectedclients.add(new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28"));

        Mockito.when(clientDao.getListOfAllClients()).thenReturn(expectedclients);

        //WHEN
        validationService.checkExistence("095 744 11 28");
    }

    @Test
    public void testcheckExistenceWithNonExistingNumber() throws BisnessException {

        //GIVEN
        long id = 1;
        List expectedclients = new ArrayList();
        expectedclients.add(new Client("test", "test", 10, "ghfg@bgf.fg", "095 744 11 28"));

        Mockito.when(clientDao.getListOfAllClients()).thenReturn(expectedclients);

        //WHEN
        validationService.checkExistence("095 744 11 29");
    }
}
