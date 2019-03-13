//package com.drizhiruk.validators.impl
//
//import com.drizhiruk.exceptions.BisnessException
//import com.drizhiruk.validators.ValidationService
//import org.junit.Test
//
//class ValidationServiceImplTest {
//
//    private ValidationService validationService = new ValidationServiceImpl();
//    void setUp() {
//
//        super.setUp();
//    }
//
//    @Test
//    public void testValidateAge(int age) throws BisnessException {
//
//        validationService.validateAge(50);
//    }
//
//    @Test(expected = BisnessException.class)
//
//    public  void  validateWrongAge(int age) throws BisnessException {
//
//        validationService.validateAge(300);
//
//    }
//}
