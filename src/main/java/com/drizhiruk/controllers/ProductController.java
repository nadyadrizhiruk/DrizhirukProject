package com.drizhiruk.controllers;

import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products")
    public String showAllProducts(ModelMap modelMap){
        modelMap.put("message", productService.getListOfAllProducts());
        return "printProducts";
    }


    @RequestMapping(value="/products", method= RequestMethod.POST)
    public String addProduct(@RequestParam(required = false) String kindOfMethod,
                            @RequestParam(required = false) Long id,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) Integer amount,
                            @RequestParam(required = false) BigDecimal price) {
        if ("put".equals(kindOfMethod)) {
            return modifyProduct(id,name,amount,price);
        } else if ("delete".equals(kindOfMethod)) {
            return deleteProduct(id);
        }

        productService.createProduct(name, price,amount);
        return "productsMenu";
    }

    @RequestMapping(value="/products", method= RequestMethod.PUT)
    public String modifyProduct(@RequestParam(value="id") Long id,
                               @RequestParam(value="name") String name,
                               @RequestParam(value="amount") Integer amount,
                               @RequestParam(value="price") BigDecimal price) {
        productService.modifyProduct(productService.findById(id), name, price, amount);
        return "productsMenu";
    }

    @RequestMapping(value="/products", method= RequestMethod.DELETE)
    public String deleteProduct(@RequestParam(value="id") Long id) {
        productService.removeProduct(id);
        return "productsMenu";
    }

    @GetMapping(value = "/productsMenu")
    public String showProductMenu(){
        return "productsMenu";
    }


    @RequestMapping(value = "/add-product", method=RequestMethod.GET)
    public String addProductPage() {
        return "addProduct";
    }

    @RequestMapping(value = "/modify-product", method=RequestMethod.GET)
    public String modifyProductPage() {
        return "modifyProduct";
    }

    @RequestMapping(value = "/delete-product", method=RequestMethod.GET)
    public String deleteProductPage() {
        return "deleteProduct";
    }
}