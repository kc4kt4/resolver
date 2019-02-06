package ru.kc4kt4.resolver.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kc4kt4.resolver.service.RabbitMQHandleMessageService;
import ru.kc4kt4.resolver.util.Constants;

@Configuration
public class AppConfiguration {

    private static final String HANDLE_MESSAGE_METHOD_NAME = "receiveMessage";
    private static final String ROUTING_KEY = "resolver.#";

    @Bean
    public Queue queue() {
        return new Queue(Constants.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constants.TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Constants.QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RabbitMQHandleMessageService receiver) {
        return new MessageListenerAdapter(receiver, HANDLE_MESSAGE_METHOD_NAME);
    }
}
