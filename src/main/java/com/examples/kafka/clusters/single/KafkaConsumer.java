package com.examples.kafka.clusters.single;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Profile({"2.1", "2.2"})
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "topic-1")
    public void onMessage(
            @Header(value = KafkaHeaders.RECEIVED_PARTITION_ID) String partition,
            @Header(KafkaHeaders.OFFSET) String offset,
            @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key,
            @Payload String message) {
        log.info("Message received on partition {}, offset {} with key {}: {}",
                partition,
                offset,
                key,
                message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
