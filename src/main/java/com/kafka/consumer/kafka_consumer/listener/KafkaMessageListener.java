package com.kafka.consumer.kafka_consumer.listener;

import com.kafka.consumer.kafka_consumer.model.KafkaMessage;
import com.kafka.consumer.kafka_consumer.model.MessageUpdated;
import com.kafka.consumer.kafka_consumer.repository.MessagesUpdatedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final MessagesUpdatedRepository messagesRepository;

    @KafkaListener(
            topics = "${app.kafka.kafkaMessageTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory",
            autoStartup = "${listen.auto.start:true}"
    )
    public void listen(@Payload KafkaMessage kafkaMessage,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        log.info("Received message: {}", kafkaMessage);
        log.info("Key: {}, Partition: {}, Topic: {}. Timestamp: {}", key, partition, topic, timestamp);

        MessageUpdated message = new MessageUpdated();
        message.setCode(kafkaMessage.getCode());
        message.setLabel(kafkaMessage.getLabel());
        messagesRepository.save(message);
    }
}
