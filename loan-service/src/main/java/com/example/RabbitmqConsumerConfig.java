package com.example;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConsumerConfig {

    @Value(value = "${spring.rabbitmq.price-queue}")
    private String queueName;

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange(queueName);
    }

    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(fanout);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}