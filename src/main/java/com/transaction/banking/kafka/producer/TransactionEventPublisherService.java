package com.transaction.banking.kafka.producer;

import com.transaction.banking.domain.Transaction;
import com.transaction.banking.dto.TransactionEventDTO;
import com.transaction.banking.enums.TransactionEventType;
import com.transaction.banking.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionEventPublisherService {

    private final KafkaProducerService kafkaProducerService;

    public void publishCreated(Transaction transaction){
        TransactionEventDTO event = TransactionEventDTO.builder()
                .transactionId(transaction.getId())
                .fromAccountId(transaction.getFromAccountId())
                .toAccountId(transaction.getToAccountId())
                .amount(transaction.getAmount())
                .timestamp(LocalDateTime.now())
                .eventType(TransactionEventType.TRANSACTION_CREATED)
                .build();

        log.info("Publicando evento TRANSACTION_CREATED para transação {}", transaction.getId());
        kafkaProducerService.publishTransactionEvent(event);
    }
}
