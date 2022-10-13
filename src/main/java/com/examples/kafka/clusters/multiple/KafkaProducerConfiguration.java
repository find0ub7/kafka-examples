package com.examples.kafka.clusters.multiple;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Profile("3")
@Configuration
public class KafkaProducerConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.kafka.cluster1")
    public KafkaProperties cluster1KafkaProperties() {
        return new KafkaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.kafka.cluster2")
    public KafkaProperties cluster2KafkaProperties() {
        return new KafkaProperties();
    }

    @Bean
    public ProducerFactory<String, String> cluster1ProducerFactory(@Qualifier("cluster1KafkaProperties") KafkaProperties cluster1KafkaProperties) {
        return new DefaultKafkaProducerFactory<>(cluster1KafkaProperties.buildProducerProperties());
    }

    @Bean
    public ProducerFactory<String, String> cluster2ProducerFactory(@Qualifier("cluster2KafkaProperties") KafkaProperties cluster2KafkaProperties) {
        return new DefaultKafkaProducerFactory<>(cluster2KafkaProperties.buildProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, String> cluster1KafkaTemplate(ProducerFactory<String, String> cluster1ProducerFactory) {
        return new KafkaTemplate<>(cluster1ProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, String> cluster2KafkaTemplate(ProducerFactory<String, String> cluster2ProducerFactory) {
        return new KafkaTemplate<>(cluster2ProducerFactory);
    }
}
