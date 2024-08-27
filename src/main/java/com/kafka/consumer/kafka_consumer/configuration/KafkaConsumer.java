//package com.kafka.consumer.kafka_consumer.configuration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kafka.consumer.kafka_consumer.model.KafkaMessage;
//import com.kafka.consumer.kafka_consumer.model.MessageUpdated;
//import com.kafka.consumer.kafka_consumer.repository.MessagesUpdatedRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Slf4j
//@Configuration
//@EnableKafka
//@RequiredArgsConstructor
//public class KafkaConsumer {
//    private final MessagesUpdatedRepository messagesRepository;
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${app.kafka.kafkaMessageGroupId}")
//    private String kafkaMessageGroupId;
//
//    @Bean
//    public ConsumerFactory<String, KafkaMessage> kafkaMessageConsumerFactory(ObjectMapper objectMapper) {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                bootstrapServers);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
//            groupId = "${app.kafka.kafkaMessageGroupId}")
//    void listener(@Payload List<KafkaMessage> kafkaMessages,
//                  @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
//                  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
//                  @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp,
//                  @Header(KafkaHeaders.OFFSET) int offset) {
//        log.info("Received message [{}] from group1, partition-{} with offset-{}",
//                kafkaMessages,
//                partition,
//                offset);
//        for(KafkaMessage kafkaMessage: kafkaMessages) {
//            log.info("Received message: {}", kafkaMessage);
//                   log.info("Key: {}, Partition: {}, Topic: {}. Timestamp: {}", key, partition, topic, timestamp);
//
//            MessageUpdated message = new MessageUpdated();
//            message.setCode(kafkaMessage.getCode());
//            message.setLabel(kafkaMessage.getLabel());
//            messagesRepository.save(message);
//        }
//    }
//}
