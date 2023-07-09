package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RateProducer {
    @Autowired
    private KafkaTemplate<String, Price> kafkaTemplate;

    @Value(value = "${spring.kafka.price-topic}")
    private String topicName;

    @Scheduled(fixedRate = 10*1000)
    public Price get() {
        double value = Math.round(new Random().nextDouble() * 10000) / 100.0;
        Price p = new Price("USD", value);
        kafkaTemplate.send(topicName, p);
        System.out.println(p);
        return p;
    }
}
