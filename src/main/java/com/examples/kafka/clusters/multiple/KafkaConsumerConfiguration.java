package com.examples.kafka.clusters.multiple;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Profile("3")
@Configuration
public class KafkaConsumerConfiguration {

    @Bean("cluster1KafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
        cluster1KafkaListenerContainerFactory(
            @Qualifier("cluster1KafkaProperties") KafkaProperties cluster1KafkaProperties) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(cluster1KafkaProperties.buildConsumerProperties()));
        factory.setConcurrency(cluster1KafkaProperties.getListener().getConcurrency());
        factory.setMissingTopicsFatal(cluster1KafkaProperties.getListener().isMissingTopicsFatal());
        return factory;
    }

    @Bean("cluster2KafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
        cluster2KafkaListenerContainerFactory(
            @Qualifier("cluster2KafkaProperties") KafkaProperties cluster2KafkaProperties) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(cluster2KafkaProperties.buildConsumerProperties()));
        factory.setConcurrency(cluster2KafkaProperties.getListener().getConcurrency());
        factory.setMissingTopicsFatal(cluster2KafkaProperties.getListener().isMissingTopicsFatal());
        return factory;
    }
}
