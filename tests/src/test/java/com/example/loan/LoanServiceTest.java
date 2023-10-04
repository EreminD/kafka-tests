package com.example.loan;

import org.apache.groovy.util.Maps;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoanServiceTest {
    private static KafkaProducer<String, String> producer;

    @BeforeAll
    public static void connect() throws UnknownHostException {
        Properties config = new Properties();
        config.put("client.id", InetAddress.getLocalHost().getHostName());
        config.put("bootstrap.servers", "localhost:29092");
        config.put("acks", "all");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(config);
    }

    @AfterAll
    public static void disconnect() {
        producer.close();
    }

    @Test
    public void test() {
        double price = 100.0;
        String message = "{\"currency\":\"USD\",\"price\": " + price + "}";
        Map<String, String> headers = Maps.of("__TypeId__", "com.example.Price");
        sendMessage("prices", message, headers);

        given()
                .get("http://localhost:8082/loan")
                .then()
                .statusCode(200)
                .body("totalPrice", equalTo((float) (price * 1.05)));
    }

    private static void sendMessage(String topic, String message) {
        sendMessage(topic, message, Collections.emptyMap());
    }

    private static void sendMessage(String topic, String message, Map<String, String> headers) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        headers.forEach((k, v) -> record.headers().add(k, v.getBytes()));
        producer.send(record);
    }
}
