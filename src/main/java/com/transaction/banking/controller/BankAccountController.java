package com.transaction.banking.controller;

import com.transaction.banking.dto.request.BankAccountRequestDTO;
import com.transaction.banking.dto.response.BankAccountResponseDTO;
import com.transaction.banking.dto.response.BankAccountListResponse;
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

@RequestMapping("/v1/account")
public interface BankAccountController {

    @PostMapping
    @Operation(summary = "Cria uma nova conta bancária")
    @ApiResponse(responseCode = "201", description = "Conta criada com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BankAccountResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    ResponseEntity<BankAccountResponseDTO> createAccount(@Valid @RequestBody BankAccountRequestDTO bankAccountRequestDTO);
    @GetMapping("/list")
    @Operation(summary = "Retorma a lista de contas")
    @ApiResponse(responseCode = "200", description = "Lista de contas retornadas com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BankAccountListResponse.class)))
    ResponseEntity<BankAccountListResponse> getAccounts();
    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma conta pelo ID")
    @ApiResponse(responseCode = "200", description = "Conta encontrada com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BankAccountResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<BankAccountResponseDTO> getAccountId(@PathVariable UUID id);
}

