package com.examples.kafka.clusters.single;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;

@Profile({"1","2.1", "2.2"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaTemplate<String, String> producer;

    @PostMapping("/topics/{topic}/messages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendToTopic(@PathVariable String topic, @RequestBody String message) {
        handleCallback(producer.send(topic, message));
    }

    @PostMapping("/topics/{topic}/keys/{key}/messages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendToTopicAtKey(@PathVariable String topic,
                                 @PathVariable String key,
                                 @RequestBody String message) {
        handleCallback(producer.send(topic, key, message));
    }

    @PostMapping("/topics/{topic}/keys/{key}/partitions/{partition}/messages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendToTopicAtKeyAndPartition(@PathVariable String topic,
                                             @PathVariable String key,
                                             @PathVariable Integer partition,
                                             @RequestBody String message) {
        handleCallback(producer.send(topic, partition, key, message));
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
