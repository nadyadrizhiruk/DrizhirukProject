package com.drizhiruk.controllers;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import com.drizhiruk.exceptions.BisnessException;
import com.drizhiruk.services.ClientService;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String showAllOrders(ModelMap modelMap, @RequestParam(required = false) Long id) {
        modelMap.put("message", orderService.findAllOrdersByClient(clientService.findById(id)));
        return "printOrders";
    }


    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public String addOrder(ModelMap modelMap,
                           @RequestParam(required = false) String kindOfMethod,
                           @RequestParam(required = false) Long id,
                           @RequestParam(required = false) Long clientId,
                           @RequestParam(required = false) String date,
                           @RequestParam Map<String, String> allRequestParams) {

        if ("get".equals(kindOfMethod)) {
            return showAllOrders(modelMap, clientId);
        } else if ("put".equals(kindOfMethod)) {
            return modifyOrder(id, clientId, date, allRequestParams);
        } else if ("delete".equals(kindOfMethod)) {
            return deleteOrder(id);
        }

        Client client = clientService.findById(clientId);
        Order order = orderService.createOrderObject(client, date, new ArrayList<>());

        for (int i = 1; allRequestParams.containsKey("productId" + i) & !allRequestParams.get("productId" + i).equals(""); i++) {

            String productId = allRequestParams.get("productId" + i);
            String price = allRequestParams.get("price" + i);
            String amount = allRequestParams.get("amount" + i);

            Product product = productService.findById(Long.parseLong(productId));
            ProductInOrder productInOrder = new ProductInOrder(product, new BigDecimal(price), Integer.parseInt(amount), order);

            order.addToProducts(productInOrder);
        }
        orderService.saveNewOrder(order);
        return "ordersMenu";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.PUT)
    public String modifyOrder(@RequestParam(required = false) Long id,
                              @RequestParam(required = false) Long clientId,
                              @RequestParam(required = false) String date,
                              @RequestParam Map<String, String> allRequestParams){

        Order order = orderService.findById(id);
        Client client = clientService.findById(clientId);

        order.setProducts(new ArrayList<>());

        for (int i = 1; allRequestParams.containsKey("productId" + i) & !allRequestParams.get("productId" + i).equals(""); i++) {

            String productId = allRequestParams.get("productId" + i);
            String price = allRequestParams.get("price" + i);
            String amount = allRequestParams.get("amount" + i);

            Product product= productService.findById(Long.parseLong(productId));
            ProductInOrder productInOrder= new ProductInOrder(product,new BigDecimal(price),Integer.parseInt(amount),order);

            order.addToProducts(productInOrder);
        }
        orderService.modifyOrder(order,client,date);
        return "ordersMenu";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.DELETE)
    public String deleteOrder(@RequestParam(value = "id") Long id) {
        orderService.removeOrder(id);
        return "ordersMenu";
    }

    @GetMapping(value = "/ordersMenu")
    public String showOrderMenu() {
        return "ordersMenu";
    }


    @RequestMapping(value = "/add-order", method = RequestMethod.GET)
    public String addOrderPage() {
        return "addOrder";
    }

    @RequestMapping(value = "/modify-order", method = RequestMethod.GET)
    public String modifyOrderPage() {
        return "modifyOrder";
    }

    @RequestMapping(value = "/delete-order", method = RequestMethod.GET)
    public String deleteOrderPage() {
        return "deleteOrder";
    }

    @RequestMapping(value = "/print-orers-by-id", method = RequestMethod.GET)
    public String printOrderPage() {
        return "printOrersById";
    }
}