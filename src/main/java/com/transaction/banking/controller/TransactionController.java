package com.transaction.banking.controller;

import com.transaction.banking.dto.request.TransactionRequestDTO;
import com.transaction.banking.dto.response.TransactionResponseDTO;
import com.transaction.banking.dto.response.TransactionListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/v1/transaction")
public interface TransactionController {

    @PostMapping
    @Operation(summary = "Cria uma nova transação")
    @ApiResponse(responseCode = "201", description = "Transação realizada com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TransactionResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO);

    @GetMapping("/list")
    @Operation(summary = "Retorna uma lista das transações")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TransactionListResponse.class)))
    ResponseEntity<TransactionListResponse> getTransactions();

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma transação pelo ID")
    @ApiResponse(responseCode = "200", description = "Transação retornada com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TransactionResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    ResponseEntity<TransactionResponseDTO> getTransactionId(@PathVariable UUID id);
}
