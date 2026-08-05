package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.integration.IntegrationOperationEnum;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogContext;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.tce.enums.TceStatusEnvioEnum;
import br.com.marhasoft.integrationhub.core.tce.mapper.TceEnvioMapper;
import br.com.marhasoft.integrationhub.core.tce.model.*;
import br.com.marhasoft.oauth.client.api.AccessTokenService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.time.LocalDate;
import java.time.Month;

/**
 * Cliente responsável pelo gerenciamento do ciclo de vida dos protocolos
 * de envio do SAGRES.
 *
 * <p>Realiza a abertura, consulta e solicitação de consolidação dos
 * protocolos de envio.</p>
 *
 * <p>Após a abertura do protocolo, as entidades de negócio são transmitidas
 * pelos clientes especializados de cada módulo, como
 * {@link TceContabilidadeClient}.</p>
 */
@Component
public class TceEnvioClient extends AbstractTceRestClient {

    private final TceEnvioMapper mapper;

    public TceEnvioClient(AccessTokenService accessTokenService,
            TceSagresProperties tceSagresProperties,
            TceErrorHandler errorHandler,
            TceEnvioMapper mapper) {

        super(accessTokenService, tceSagresProperties, errorHandler);

        this.mapper = mapper;
    }

    /**
     * Abre um novo protocolo de envio no SAGRES.
     */
    public TceEnvioResult criarEnvio(
            IntegrationContext<?, ?> context,
            TipoEnvio tipoEnvio) {

        TceEnvioRequest request = criarRequest(context, tipoEnvio);

        String uri = SagresUris.envios();

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.CRIAR_ENVIO,
                HttpMethod.POST,
                uri,
                context,
                request);

        registrarRequisicao(logContext);

        try {

            TceEnvioResponse response =
                    restClient(context)
                            .post()
                            .uri(uri)
                            .body(request)
                            .retrieve()
                            .body(TceEnvioResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(response);

        } catch (RestClientResponseException ex) {

            return registrarErroECriarResultado(logContext, ex);
        }
    }

    /**
     * Consulta um envio pelo protocolo.
     */
    public TceEnvioResult consultarEnvioPorProtocolo(IntegrationContext<?, ?> context,
                                                     String protocoloEnvio) {

        String uri = SagresUris.envio(protocoloEnvio);

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.CONSULTAR_ENVIO_POR_PROTOCOLO,
                HttpMethod.GET,
                uri,
                context,
                null);

        logContext.setProtocolo(protocoloEnvio);

        registrarRequisicao(logContext);

        try {

            TceEnvioResponse response =
                    restClient(context)
                            .get()
                            .uri(uri)
                            .retrieve()
                            .body(TceEnvioResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(response);

        } catch (RestClientResponseException ex) {

            return registrarErroECriarResultado(logContext, ex);
        }
    }

    /**
     * Consulta um envio pelo código da Unidade Gestora,
     * tipo de envio e competência.
     */
    public TceEnvioResult consultarEnvioPorUnidadeGestoraTipoCompetencia(
            IntegrationContext<?, ?> context,
            String codigoUnidadeGestora,
            TipoEnvio tipoEnvio,
            LocalDate competencia) {

        String uri =
                SagresUris.envioPorUnidadeGestoraTipoCompetencia(
                        codigoUnidadeGestora,
                        tipoEnvio,
                        competencia);

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.CONSULTAR_ENVIO_POR_UG_TIPO_COMPETENCIA,
                HttpMethod.GET,
                uri,
                context,
                null);

        registrarRequisicao(logContext);

        try {

            TceEnvioResponse response =
                    restClient(context)
                            .get()
                            .uri(uri)
                            .retrieve()
                            .body(TceEnvioResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(response);

        } catch (RestClientResponseException ex) {

            return registrarErroECriarResultado(logContext, ex);
        }
    }


    /**
     * Solicita ao SAGRES o processamento do protocolo informado.
     *
     * <p>A consolidação é assíncrona. Após a solicitação, o envio é marcado
     * como {@link TceStatusEnvioEnum#EM_PROCESSAMENTO} até que uma nova
     * consulta retorne o resultado definitivo.</p>
     */
    public TceEnvio consolidar(IntegrationContext<?, ?> context, TceEnvio envio) {

        String uri = SagresUris.consolidacoes(envio.getProtocoloEnvio());

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.CONSOLIDAR_ENVIO,
                HttpMethod.POST,
                uri,
                context,
                null);

        logContext.setProtocolo(envio.getProtocoloEnvio());

        registrarRequisicao(logContext);

        try {

            restClient(context)
                    .post()
                    .uri(uri)
                    .retrieve()
                    .toBodilessEntity();

            // A consolidação ocorre de forma assíncrona.
            // Após a solicitação, o envio passa para EM_PROCESSAMENTO.
            envio.setStatusEnvio(TceStatusEnvioEnum.EM_PROCESSAMENTO);

            registrarSucesso(logContext, null);

            return envio;

        } catch (RestClientResponseException ex) {

            registrarErro(logContext, ex);
            throw ex;
        }
    }


    /**
     * Cria o payload de abertura do protocolo.
     */
    private TceEnvioRequest criarRequest(IntegrationContext<?, ?> context, TipoEnvio tipoEnvio) {

        return TceEnvioRequest.builder()
                .tipoEnvio(tipoEnvio)
                .competencia(
                        LocalDate.of(context.getExercicio(), Month.JANUARY, 1))
                .build();
    }

    /**
     * Cria um resultado de sucesso a partir da resposta do SAGRES.
     */
    private TceEnvioResult criarResultadoSucesso(TceEnvioResponse response) {

        return TceEnvioResult.builder()
                .envio(mapper.toModel(response))
                .result(sucesso())
                .build();
    }

    /**
     * Registra o erro retornado pelo SAGRES e cria o resultado
     * da integração correspondente.
     */
    private TceEnvioResult registrarErroECriarResultado(SagresLogContext logContext,
            RestClientResponseException ex) {

        registrarErro(logContext, ex);

        return TceEnvioResult.builder()
                .result(errorHandler.handle(ex))
                .build();
    }
}