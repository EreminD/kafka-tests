package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RateProducer {
    @Autowired
    private KafkaTemplate<String, Price> kafkaTemplate;

    @Value(value = "${spring.kafka.price-topic}")
    private String topicName;

    @Value(value = "${price.auto-publisher.enabled}")
    private boolean autoPublishEnabled;

    @Value(value = "${price.auto-publisher.min_price}")
    private double min;

    @Value(value = "${price.auto-publisher.max_price}")
    private double max;

    @Scheduled(fixedRate = 10*1000)
    public void get() {
        if (autoPublishEnabled) {
            double value = new Random().doubles(min, max).findFirst().getAsDouble();
            Price p = new Price("USD", value);
            kafkaTemplate.send(topicName, p);
            System.out.println(p);
        }
    }
}
