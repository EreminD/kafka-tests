package com.example;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class RabbitProducerConfig {
    @Value(value = "${spring.rabbitmq.price-queue}")
    private String queue;

    @Bean
    public Queue myQueue() {
        return new Queue(queue, false);
    }
}