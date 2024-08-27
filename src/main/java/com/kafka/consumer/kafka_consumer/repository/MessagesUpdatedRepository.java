package com.kafka.consumer.kafka_consumer.repository;

import com.kafka.consumer.kafka_consumer.model.MessageUpdated;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesUpdatedRepository extends JpaRepository<MessageUpdated,Long> {
}
