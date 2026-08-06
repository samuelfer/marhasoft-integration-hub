package br.com.marhasoft.integrationhub.core.integration.sagres;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.integration.IntegrationOperationEnum;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class SagresLogContext {

    @Builder.Default
    private String requestId = UUID.randomUUID().toString();

    @Builder.Default
    private Instant startTime = Instant.now();

    private IntegrationOperationEnum operation;

    private HttpMethod method;

    private String url;

    private IntegrationContext<?, ?> context;

    private String protocolo;

    private Object request;

    private Object response;

    private Integer status;

    private IntegrationResult result;

    private Object error;

}