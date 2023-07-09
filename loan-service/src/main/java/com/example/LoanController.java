package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class LoanController {
    private Price currentPrice = new Price();
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "${spring.kafka.price-topic}", groupId = "deposit")
    public void listenGroup(@Payload Price message) {
        System.out.println("Received Message in group deposit: " + message);
        currentPrice = message;
    }

    @GetMapping("/loan")
    public LoanInfo getLoanInfo() {
        return new LoanInfo(currentPrice, currentPrice.getPrice()*1.05);
    }
}
