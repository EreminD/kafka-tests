package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class LoanController {
    private Price currentPrice = new Price();
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "${spring.kafka.price-topic}", groupId = "loan")
    public void listenGroup(String message) {
        try {
            System.out.println("Received Message in group loan: " + message);
            currentPrice = mapper.readValue(message, Price.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error on JSON parsing: " + message);
            System.err.println(e);
        }
    }

    @GetMapping("/loan")
    public LoanInfo getLoanInfo() {
        return new LoanInfo(currentPrice, currentPrice.getPrice()*1.05);
    }
}
