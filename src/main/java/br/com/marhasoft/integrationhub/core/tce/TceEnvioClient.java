package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.integration.IntegrationOperationEnum;
import br.com.marhasoft.integrationhub.core.integration.IntegrationTypeEnum;
import br.com.marhasoft.integrationhub.core.integration.JsonUtils;
import br.com.marhasoft.integrationhub.core.integration.sagres.IntegrationLog;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogContext;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogger;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.tce.enums.TceStatusEnvioEnum;
import br.com.marhasoft.integrationhub.core.tce.mapper.TceEnvioMapper;
import br.com.marhasoft.integrationhub.core.tce.model.*;
import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TceEnvioClient {
    private final AccessTokenService accessTokenService;
    private final TceEnvioMapper mapper;
    private final TceErrorHandler errorHandler;
    private final TceSagresProperties tceSagresProperties;

    /**
     * Etapa 1 do fluxo do SAGRES.
     * Solicita a abertura de um protocolo de envio que será utilizado
     * posteriormente para o envio das entidades.
     */
    public TceEnvioResult criarEnvio(
            IntegrationContext<?, ?> context,
            TipoEnvio tipoEnvio) {

        TceEnvioRequest request = criarRequest(context, tipoEnvio);
        System.out.println("CRIANDO O REQUEST "+request);
        SagresLogContext logContext =
                SagresLogContext.builder()
                        .operation(IntegrationOperationEnum.CRIAR_ENVIO)
                        .method(HttpMethod.POST)
                        .url(tceSagresProperties.getBaseUrl() + "/envios")
                        .context(context)
                        .request(request)
                        .build();

        SagresLogger.request(logContext);

        try {
            TceEnvioResponse response =
                    restClient(context)
                            .post()
                            .uri("/envios")
                            .body(request)
                            .retrieve()
                            .body(TceEnvioResponse.class);

            logContext.setStatus(HttpStatus.OK.value());
            logContext.setResponse(response);
            logContext.setProtocolo(response.getProtocoloEnvio());

            SagresLogger.response(logContext);

            return TceEnvioResult.builder()
                    .envio(mapper.toModel(response))
                    .result(new IntegrationResult())
                    .build();

        } catch (RestClientResponseException ex) {
            logContext.setStatus(ex.getStatusCode().value());
            logContext.setError(ex.getResponseBodyAsString());

            SagresLogger.error(logContext, ex);

            return criarResultadoErro(ex);
        }
    }

    /**
     * Etapa 3 do fluxo do SAGRES.
     * Solicita o processamento e a validação dos dados enviados
     * para o protocolo informado.
     */
    public TceEnvio consolidar(
            IntegrationContext<?, ?> context,
            TceEnvio envio) {

        log.info(
                "Solicitando consolidação do protocolo {}.",
                envio.getProtocoloEnvio());

        restClient(context)
                .post()
                .uri(
                        "/envios/{protocolo}/consolidacoes",
                        envio.getProtocoloEnvio())
                .retrieve()
                .toBodilessEntity();

        envio.setStatusEnvio(
                TceStatusEnvioEnum.EM_PROCESSAMENTO);

        log.info(
                "Consolidação solicitada para o protocolo {}.",
                envio.getProtocoloEnvio());

        return envio;
    }

    /**
     * Consulta um envio existente.
     */
    public TceEnvioResult consultar(
            IntegrationContext<?, ?> context,
            String protocoloEnvio) {

        log.info(
                "Consultando protocolo {}.",
                protocoloEnvio);

        try {

            TceEnvioResponse response =
                    restClient(context)
                            .get()
                            .uri(
                                    "/envios/{protocoloEnvio}",
                                    protocoloEnvio)
                            .retrieve()
                            .body(TceEnvioResponse.class);

            log.info(
                    "Protocolo {} consultado. Status: {}",
                    protocoloEnvio,
                    response.getStatusEnvio());

            return TceEnvioResult.builder()
                    .envio(mapper.toModel(response))
                    .result(new IntegrationResult())
                    .build();

        } catch (RestClientResponseException ex) {

            log.error(
                    "Erro ao consultar protocolo {}. Status: {}. Resposta: {}",
                    protocoloEnvio,
                    ex.getStatusCode(),
                    ex.getResponseBodyAsString());

            return criarResultadoErro(ex);
        }
    }

    /**
     * Cria o request utilizado para abertura do protocolo.
     */
    private TceEnvioRequest criarRequest(
            IntegrationContext<?, ?> context,
            TipoEnvio tipoEnvio) {

        return TceEnvioRequest.builder()
                .tipoEnvio(tipoEnvio)
                .competencia(
                        LocalDate.of(
                                context.getExercicio(),
                                1,
                                1))
                .build();
    }

    /**
     * Cria um resultado de erro a partir da resposta do TCE.
     */
    private TceEnvioResult criarResultadoErro(
            RestClientResponseException ex) {

        return TceEnvioResult.builder()
                .result(errorHandler.handle(ex))
                .build();
    }

    /**
     * Obtém o RestClient autenticado para comunicação com o TCE.
     */
    private RestClient restClient(
            IntegrationContext<?, ?> context) {

        String token = accessTokenService.getAccessToken(
                context.getAuthentication().getClient());

        return RestClient.builder()
                .baseUrl(tceSagresProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }
}