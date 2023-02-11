package com.belum.apitemplate.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bel-sahn on 7/30/19
 */
public class SubscriberConfig {
//region PROPERTIES
    private final Environment env;
//endregion

//region CONSTRUCTORS
    public SubscriberConfig(Environment env) {
        this.env = env;
    }
//endregion

//region BEANS
    @Bean
    Map<String, Object> subscriberProps() {
        final Map<String, Object> props = new HashMap<>();
        return props;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(subscriberProps(),
                new StringDeserializer(),
                new JsonDeserializer<>());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(1);
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
//endregion

//region HELPER METHODS
//endregion
}
