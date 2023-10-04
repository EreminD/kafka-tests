package com.example.loan;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoanServiceTest {
    private static KafkaProducer<String, String> producer;

    @BeforeAll
    public static void kafka() throws UnknownHostException {
        Properties config = new Properties();
        config.put("client.id", InetAddress.getLocalHost().getHostName());
        config.put("bootstrap.servers", "localhost:29092");
        config.put("acks", "all");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(config);
    }

    @Test
    public void test() throws ExecutionException, InterruptedException, TimeoutException {

        double price = 100.0;
        String message = "{\"currency\":\"USD\",\"price\": " + price + "}";

        sendMessage("prices", message);

        given()
                .get("http://localhost:8082/loan")
                .then()
                .statusCode(200)
                .body("totalPrice", equalTo((float) (price * 1.05)));
    }

    private static void sendMessage(String topic, String message) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        record.headers().add("__TypeId__", "com.example.Price".getBytes());
        producer.send(record);
    }
}
