package com.example;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class LoanController {
    private Price currentPrice = new Price();

    @RabbitListener(queues = "${spring.rabbitmq.price-queue}")
    public void listenGroup(@Payload Price message) {
        System.out.println("Received Message loan: " + message);
        currentPrice = message;
    }

    @GetMapping("/loan")
    public LoanInfo getLoanInfo() {
        return new LoanInfo(currentPrice, currentPrice.getPrice() * 1.05);
    }
}
