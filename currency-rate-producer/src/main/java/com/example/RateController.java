package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class RateController {

    @Autowired
    private KafkaTemplate<String, Price> kafkaTemplate;

    @Value(value = "${spring.kafka.price-topic}")
    private String topicName;

    @GetMapping("/rate/{price}")
    public Price setPrice(@PathVariable("price") double price) {
        Price p = new Price("USD", price);
        kafkaTemplate.send(topicName, p);
        System.out.println(p);
        return p;
    }
}
