package com.example;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RateProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private FanoutExchange fanout;
    @Value(value = "${price.auto-publisher.enabled}")
    private boolean autoPublishEnabled;
    @Value(value = "${price.auto-publisher.min_price}")
    private double min;
    @Value(value = "${price.auto-publisher.max_price}")
    private double max;

    @Scheduled(fixedRate = 10 * 1000)
    public void get() {
        if (autoPublishEnabled) {
            double value = new Random().doubles(min, max).findFirst().getAsDouble();
            Price p = new Price("USD", value);
            rabbitTemplate.convertAndSend(fanout.getName(), "", p);
            System.out.println(p);
        }
    }
}
