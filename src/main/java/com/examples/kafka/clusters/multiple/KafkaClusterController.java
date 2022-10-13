package com.examples.kafka.clusters.multiple;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;

@Profile("3")
@Slf4j
@RestController
@RequestMapping("/clusters/{clusterNumber}")
@RequiredArgsConstructor
public class KafkaClusterController {

    private final KafkaTemplate<String, String> cluster1KafkaTemplate;

    private final KafkaTemplate<String, String> cluster2KafkaTemplate;

    @PostMapping("/topics/{topic}/messages")
    public void sendToTopic(@PathVariable Integer clusterNumber, @PathVariable String topic, @RequestBody String message) {
        handleCallback(getProducer(clusterNumber).send(topic, message));
    }

    @PostMapping("/topics/{topic}/keys/{key}/messages")
    public void sendToTopicAtKey(@PathVariable Integer clusterNumber,
                                 @PathVariable String topic,
                                 @PathVariable String key,
                                 @RequestBody String message) {
        handleCallback(getProducer(clusterNumber).send(topic, key, message));
    }

    @PostMapping("/topics/{topic}/keys/{key}/partitions/{partition}/messages")
    public void sendToTopicAtKeyAndPartition(@PathVariable Integer clusterNumber,
                                             @PathVariable String topic,
                                             @PathVariable String key,
                                             @PathVariable Integer partition,
                                             @RequestBody String message) {
        handleCallback(getProducer(clusterNumber).send(topic, partition, key, message));
    }

    private KafkaTemplate<String, String> getProducer(Integer clusterNumber) {
        return switch (clusterNumber) {
            case 1 -> cluster1KafkaTemplate;
            case 2 -> cluster2KafkaTemplate;
            default -> throw new RuntimeException("Invalid cluster: " + clusterNumber + ". Valid numbers: 1 or 2");
        };
    }

    private void handleCallback(ListenableFuture<SendResult<String, String>> future) {
        future.addCallback(handleSuccess(), handleFailure());
    }

    private SuccessCallback<? super SendResult<String, String>> handleSuccess() {
        return (SuccessCallback<SendResult<String, String>>) successResult ->
                log.info("Message successfully sent: {}", successResult.getProducerRecord());
    }

    private FailureCallback handleFailure() {
        return throwable -> log.error("Fail to send message:", throwable);
    }
}
