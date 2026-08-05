package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.integration.IntegrationOperationEnum;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogContext;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogger;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.result.IntegrationStatus;
import br.com.marhasoft.integrationhub.core.tce.model.TceEnvioResponse;
import br.com.marhasoft.oauth.client.api.AccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractTceRestClient {

    protected final AccessTokenService accessTokenService;

    protected final TceSagresProperties tceSagresProperties;

    protected final TceErrorHandler errorHandler;

    protected RestClient restClient(IntegrationContext<?, ?> context) {

        String token = accessTokenService.getAccessToken(
                context.getAuthentication().getClient());

        return RestClient.builder()
                .baseUrl(tceSagresProperties.getBaseUrl())
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token)
                .build();
    }

    protected SagresLogContext criarLogContext(IntegrationOperationEnum operation,
            HttpMethod method,
            String uri,
            IntegrationContext<?, ?> context,
            Object request) {

        return SagresLogContext.builder()
                .operation(operation)
                .method(method)
                .url(tceSagresProperties.getBaseUrl() + uri)
                .context(context)
                .request(request)
                .build();
    }

    /**
     * Cria um resultado de sucesso.
     */
    protected IntegrationResult sucesso() {

        IntegrationResult result = new IntegrationResult();
        result.setStatus(IntegrationStatus.SUCCESS);

        return result;
    }

    protected void registrarSucesso(SagresLogContext logContext) {

        registrarSucesso(logContext, null);
    }

    protected void registrarSucesso(SagresLogContext logContext, TceEnvioResponse response) {

        logContext.setStatus(HttpStatus.OK.value());
        if (response != null) {
            logContext.setResponse(response);
            logContext.setProtocolo(response.getProtocoloEnvio());
        } else {
            logContext.setProtocolo(logContext.getProtocolo());
        }

        SagresLogger.response(logContext);
    }

    protected void registrarErro(SagresLogContext logContext,
            RestClientResponseException ex) {

        logContext.setStatus(ex.getStatusCode().value());
        logContext.setError(ex.getResponseBodyAsString());

        SagresLogger.error(logContext, ex);
    }

    protected void registrarRequisicao(SagresLogContext logContext) {

        SagresLogger.request(logContext);
    }
}