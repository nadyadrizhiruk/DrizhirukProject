package com.drizhiruk.services.impl;
import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Product;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.ProductService;
import com.drizhiruk.validators.ValidationService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)

public class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    private ValidationService validationService = Mockito.mock(ValidationService.class);
    private ProductService productService;

    @Before
    public void init() {

        productService = new ProductServiceImpl(productDao, validationService);

    }


    @Test

    public void findById_RightIdTest() {

        //GIVEN
        long id = 1;
        Product expectedProduct = new Product("test", new BigDecimal(10), 10);

        Mockito.when(productDao.findById(id)).thenReturn(expectedProduct);

        //WHEN
        Product product = productService.findById(id);

        //Then
        Mockito.verify(productDao, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(productDao);
        Assert.assertEquals(expectedProduct, product);
    }

    @Test

    public void findById_WrongIdTest() {

        //GIVEN
        long id = -1;
        Product expectedProduct = null;

        Mockito.when(productDao.findById(id)).thenReturn(null);

        //WHEN
        Product product = productService.findById(id);

        //Then
        Mockito.verify(productDao, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(productDao);
        Assert.assertEquals(expectedProduct, product);
    }

    @Test
    public void removeProductTest() {

        //GIVEN
        long id = 1;
        boolean expectedResult = true;
        Mockito.when(productDao.removeProduct(id)).thenReturn(true);

        //WHEN
        boolean result = productService.removeProduct(id);

        //Then
        Mockito.verify(productDao, Mockito.times(1)).removeProduct(id);
        Mockito.verifyNoMoreInteractions(productDao);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void createProductTest() throws BisnessException {

        //GIVEN
        String name = "test";
        BigDecimal price = new BigDecimal(10);
        int amount = 10;

        Mockito.when(productDao.saveProduct((Product) any())).thenReturn(true);

        //WHEN
        productService.createProduct(name, price, amount);

        //Then
        Mockito.verify(productDao, Mockito.times(1)).saveProduct((Product) any());
        Mockito.verifyNoMoreInteractions(productDao);

    }

    @Test

    public void modifyProductTest() throws BisnessException {

        //GIVEN
        long id = 1;
        String name = "test1";
        BigDecimal price = new BigDecimal(10);
        int amount = 10;

        Product expectedProduct = new Product(id,name, price, amount);

        Mockito.when(productDao.saveExistingProduct(expectedProduct)).thenReturn(true);

        expectedProduct.setName("test");
        expectedProduct.setPrice(new BigDecimal(9));
        expectedProduct.setAmount(9);

        //WHEN

        productService.modifyProduct(expectedProduct,name,price,amount);

        //Then
        Mockito.verify(productDao, Mockito.times(1)).saveExistingProduct(expectedProduct);
        Mockito.verifyNoMoreInteractions(productDao);
        Assert.assertEquals(name, expectedProduct.getName());
        Assert.assertEquals(price, expectedProduct.getPrice());
        Assert.assertEquals(amount, expectedProduct.getAmount());

    }

    @Test

    public void getListOfAllProductsTest() {

        //GIVEN
        long id = 1;
        List<Product> expectedProducts = new ArrayList();
        expectedProducts.add(new Product("test", new BigDecimal(10), 10));

        Mockito.when(productDao.getListOfAllProducts()).thenReturn(expectedProducts);

        //WHEN
        List <Product> products = productService.getListOfAllProducts();

        //Then
        Mockito.verify(productDao, Mockito.times(1)).getListOfAllProducts();
        Mockito.verifyNoMoreInteractions(productDao);
        Assert.assertEquals(expectedProducts, products);
    }



    @After
    public void tearDawn() {

        productService = null;

    }



}



