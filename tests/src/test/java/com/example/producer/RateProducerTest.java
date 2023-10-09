package com.example.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RateProducerTest {

    private static KafkaConsumer<String, String> consumer;

    @BeforeAll
    public static void connect() throws UnknownHostException {
        Properties config = new Properties();
        config.put("client.id", InetAddress.getLocalHost().getHostName());
        config.put("bootstrap.servers", "localhost:29092");
        config.put("group.id", "autotest"); // generate
        config.put("enable.auto.commit", "true");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(config);
    }

    @AfterAll
    public static void disconnect() {
        consumer.close();
    }

    @Test
    public void test() throws TimeoutException {
        double price = new Random(17).nextDouble() * 100;
        given().get("http://localhost:8081/rate/" + price).then().extract().asPrettyString();

        ConsumerRecord<String, String> record = awaitMessage(
                List.of("prices"),
                r -> r.value().contains(Double.toString(price)),
                Duration.ofSeconds(10),
                "нет сообщения, содержащего " + price
        );
        assertNotNull(record.value());
    }

    private ConsumerRecord<String, String> awaitMessage(Collection<String> topics, Predicate<ConsumerRecord<String, String>> predicate, Duration duration) throws TimeoutException {
        return awaitMessage(topics, predicate, duration, "Не дождались");
    }

    private ConsumerRecord<String, String> awaitMessage(Collection<String> topics, Predicate<ConsumerRecord<String, String>> predicate, Duration duration, String message) throws TimeoutException {
        consumer.subscribe(topics);
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < duration.toMillis()) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));
            for (ConsumerRecord<String, String> record : records) {
                if (predicate.test(record)) {
                    System.out.println(record.value());
                    return record;
                }
            }
        }

        throw new TimeoutException(message);
    }
}
