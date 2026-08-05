package br.com.marhasoft.integrationhub.core.integration.sagres;

import br.com.marhasoft.integrationhub.core.integration.IntegrationEventTypeEnum;
import br.com.marhasoft.integrationhub.core.integration.IntegrationTypeEnum;
import br.com.marhasoft.integrationhub.core.integration.JsonUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
@UtilityClass
public class SagresLogger {

    public void request(SagresLogContext context) {
        log.info(JsonUtils.toJson(
                toIntegrationLog(context, IntegrationEventTypeEnum.REQUEST, null)));
    }

    public void response(SagresLogContext context) {
        log.info(JsonUtils.toJson(
                toIntegrationLog(context, IntegrationEventTypeEnum.RESPONSE, null)));
    }

    public void error(SagresLogContext context,  Throwable throwable) {
        log.error(JsonUtils.toJson(toIntegrationLog(
                context, IntegrationEventTypeEnum.ERROR, throwable)));
    }

    private IntegrationLog toIntegrationLog(
            SagresLogContext context,
            IntegrationEventTypeEnum eventType,
            Throwable throwable) {

        return IntegrationLog.builder()
                .timestamp(LocalDateTime.now())
                .requestId(context.getRequestId())
                .service("integration-hub")
                .environment("dev") // depois podemos ler do Spring
                .integration(IntegrationTypeEnum.SAGRES.name())
                .eventType(eventType.name())
                .operation(context.getOperation().name())
                .method(context.getMethod().name())
                .url(context.getUrl())
                .status(context.getStatus())
                .durationMs(Duration.between(
                        context.getStartTime(),
                        Instant.now()).toMillis())
                .client(context.getContext().getAuthentication().getClient())
                .organization(context.getContext().getOrganization().getCodigo())
                .request(context.getRequest())
                .protocolo(context.getProtocolo())
                .response(context.getResponse())
                .error(context.getError())
                .exception(
                        throwable != null ? throwable.getClass().getName() : null)
                .message(
                        throwable != null ? throwable.getMessage() : null)
                .build();
    }
}