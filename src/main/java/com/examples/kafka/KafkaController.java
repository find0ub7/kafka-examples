package com.examples.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaTemplate<String, String> producer;

    @PostMapping("/topics/{topic}/messages")
    public void sendToTopic(@PathVariable String topic, @RequestBody String message) {
        producer.send(topic, message);
    }

    @PostMapping("/topics/{topic}/keys/{key}/messages")
    public void sendToTopicAtKey(@PathVariable String topic,
                                 @PathVariable String key,
                                 @RequestBody String message) {
        producer.send(topic, key, message);
    }

    @PostMapping("/topics/{topic}/keys/{key}/partitions/{partition}/messages")
    public void sendToTopicAtKeyAndPartition(@PathVariable String topic,
                                             @PathVariable String key,
                                             @PathVariable Integer partition,
                                             @RequestBody String message) {
        producer.send(topic, partition, key, message);
    }
}
