package bg.tuplovdiv.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "order";
    public static final String ORDER_CREATE_QUEUE = "order-create-queue";
    public static final String ORDER_CREATED_QUEUE = "order-created-queue";
    public static final String ORDER_UPDATE_QUEUE = "order-update-queue";
    public static final String ORDER_UPDATED_QUEUE = "order-updated-queue";
    public static final String ORDER_CREATE_ROUTING_KEY = "order.event.create";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.event.created";
    public static final String ORDER_UPDATE_ROUTING_KEY = "order.event.update";
    public static final String ORDER_UPDATED_ROUTING_KEY = "order.event.updated";

    @Bean
    public Queue orderCreateQueue() {
        return new Queue(ORDER_CREATE_QUEUE);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE);
    }

    @Bean
    public Queue orderUpdateQueue() {
        return new Queue(ORDER_UPDATE_QUEUE);
    }

    @Bean
    public Queue orderUpdatedQueue() {
        return new Queue(ORDER_UPDATED_QUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding orderCreateBinding(@Qualifier("orderCreateQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ORDER_CREATE_ROUTING_KEY);
    }

    @Bean
    public Binding orderCreatedBinding(@Qualifier("orderCreatedQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ORDER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding orderUpdateBinding(@Qualifier("orderUpdateQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ORDER_UPDATE_ROUTING_KEY);
    }

    @Bean
    public Binding orderUpdatedBinding(@Qualifier("orderUpdatedQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ORDER_UPDATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
