package com.example;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class RabbitProducerConfig {
    @Value(value = "${spring.rabbitmq.price-queue}")
    private String queueName;

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange(queueName);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}