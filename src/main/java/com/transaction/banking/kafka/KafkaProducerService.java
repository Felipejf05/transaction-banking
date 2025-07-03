package com.transaction.banking.kafka;

import com.transaction.banking.dto.TransactionEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, TransactionEventDTO> kafkaTemplate;

    @Value("${app.kafka.topics.transaction-events}")
    private String transactionEventsTopic;

    public CompletableFuture<SendResult<String, TransactionEventDTO>> publishTransactionEvent(TransactionEventDTO event) {
        String key = event.getTransactionId().toString();

        return kafkaTemplate.send(transactionEventsTopic, key, event)
                .whenComplete((result, throwlable) -> {
                    if (throwlable != null) {
                        log.error("Erro ao publicar evento para transação {}: {}",
                                event.getTransactionId(), throwlable.getMessage());
                    } else if (result != null) {
                        log.info("Evento {} publicado para transação {} - partition: {}, offset: {}",
                                event.getEventType(), event.getTransactionId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

}
