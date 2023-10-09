package com.example;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class RateController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private FanoutExchange fanout;

    @GetMapping("/rate/{price}")
    public Price setPrice(@PathVariable("price") double price) {
        Price p = new Price("USD", price);
        rabbitTemplate.convertAndSend(fanout.getName(), p);
        System.out.println(p);
        return p;
    }
}
