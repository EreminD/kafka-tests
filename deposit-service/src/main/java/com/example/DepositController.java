package com.example;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController()
public class DepositController {
    private Price currentPrice = new Price();

    @KafkaListener(topics = "${spring.kafka.price-topic}", groupId = "deposit")
    public void listenGroup(@Payload Price message) {
        System.out.println("Received Message in group deposit: " + message);
        currentPrice = message;
    }

    @GetMapping("/deposit")
    public DepositInfo getLoanInfo() {
        return new DepositInfo(currentPrice, new Random().nextDouble());
    }
}
