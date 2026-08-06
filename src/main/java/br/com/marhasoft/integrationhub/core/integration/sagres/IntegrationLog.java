package br.com.marhasoft.integrationhub.core.integration.sagres;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationLog {

    private LocalDateTime timestamp;

    private String requestId;

    private String service;

    @Value("${spring.profiles.active:default}")
    private String environment;

    private String integration;

    private String eventType;

    private String operation;

    private String method;

    private String url;

    private Integer status;

    private Long durationMs;

    private String client;

    private String organization;

    private Object request;

    private String protocolo;

    private Object response;

    private IntegrationResult result;

    private Object error;

    private String exception;

    private String message;

}