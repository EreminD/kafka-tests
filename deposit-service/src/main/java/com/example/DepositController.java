package com.example;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController()
public class DepositController {
    private Price currentPrice = new Price();

    @RabbitListener(queues = "${spring.rabbitmq.price-queue}")
    public void listenGroup(@Payload Price message) {
        System.out.println("Received Message in deposit: " + message);
        currentPrice = message;
    }

    @GetMapping("/deposit")
    public DepositInfo getLoanInfo() {
        return new DepositInfo(currentPrice, new Random().nextDouble());
    }
}
