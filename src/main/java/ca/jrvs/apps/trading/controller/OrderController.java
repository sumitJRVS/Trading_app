/*
package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.modelRepo.domain.SecurityOrder;


import ca.jrvs.apps.trading.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService oService) {
        this.orderService = oService;
    }

    @PostMapping(path="/marketOrder")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SecurityOrder submitMarketOrder(MarketOrderDTO order){
        try{
            return orderService.executeMarketOrder(order);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}

*/