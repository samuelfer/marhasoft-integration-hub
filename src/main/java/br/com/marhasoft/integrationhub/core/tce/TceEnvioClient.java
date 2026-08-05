package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.integration.IntegrationOperationEnum;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogContext;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.tce.enums.TceStatusEnvioEnum;
import br.com.marhasoft.integrationhub.core.tce.mapper.TceEnvioMapper;
import br.com.marhasoft.integrationhub.core.tce.mapper.TceValidacaoMapper;
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
    private final TceValidacaoMapper validacaoMapper;

    public TceEnvioClient(AccessTokenService accessTokenService,
            TceSagresProperties tceSagresProperties,
            TceErrorHandler errorHandler,
            TceEnvioMapper mapper,
            TceValidacaoMapper validacaoMapper) {

        super(accessTokenService, tceSagresProperties, errorHandler);

        this.mapper = mapper;
        this.validacaoMapper = validacaoMapper;
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

            return TceEnvioResult.builder()
                    .result(criarResultadoErro(logContext, ex))
                    .build();
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

            return TceEnvioResult.builder()
                    .result(criarResultadoErro(logContext, ex))
                    .build();
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

            return TceEnvioResult.builder()
                    .result(criarResultadoErro(logContext, ex))
                    .build();
        }
    }

    /**
     * Consulta o resultado da validação de um protocolo de envio.
     *
     * <p>O SAGRES identifica cada validação por uma chave de validação,
     * retornando o status da validação e os respectivos detalhes, como
     * sumário e eventuais erros encontrados.</p>
     *
     * @param context contexto da integração
     * @param protocoloEnvio protocolo do envio
     * @param chaveValidacao chave da validação retornada pelo SAGRES
     * @return resultado da consulta da validação
     */
    public TceValidacaoResult consultarValidacoes(IntegrationContext<?, ?> context,
            String protocoloEnvio, String chaveValidacao) {

        String uri = SagresUris.validacoes(
                protocoloEnvio,
                chaveValidacao);

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.CONSULTAR_VALIDACOES,
                HttpMethod.GET,
                uri,
                context,
                null);

        logContext.setProtocolo(protocoloEnvio);

        registrarRequisicao(logContext);

        try {

            TceValidacaoResponse response =
                    restClient(context)
                            .get()
                            .uri(uri)
                            .retrieve()
                            .body(TceValidacaoResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(response);

        } catch (RestClientResponseException ex) {

            return TceValidacaoResult.builder()
                    .result(criarResultadoErro(logContext, ex))
                    .build();
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


    private TceValidacaoResult criarResultadoSucesso(
            TceValidacaoResponse response) {

        return TceValidacaoResult.builder()
                .validacao(validacaoMapper.toModel(response))
                .result(sucesso())
                .build();
    }

    /**
     * Registra o erro retornado pelo SAGRES e cria o resultado
     * base da integração.
     */
    private IntegrationResult criarResultadoErro(
            SagresLogContext logContext,
            RestClientResponseException ex) {

        registrarErro(logContext, ex);

        return errorHandler.handle(ex);
    }
}