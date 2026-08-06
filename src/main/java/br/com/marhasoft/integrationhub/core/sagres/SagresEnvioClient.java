package br.com.marhasoft.integrationhub.core.sagres;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.integration.IntegrationOperationEnum;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogContext;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.sagres.enums.TceStatusEnvioEnum;
import br.com.marhasoft.integrationhub.core.sagres.mapper.TceEnvioMapper;
import br.com.marhasoft.integrationhub.core.sagres.mapper.TceValidacaoMapper;
import br.com.marhasoft.integrationhub.core.sagres.model.*;
import br.com.marhasoft.oauth.client.api.AccessTokenService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.time.LocalDate;

/**
 * Cliente responsável pelo gerenciamento do ciclo de vida dos protocolos
 * de envio do SAGRES.
 *
 * <p>Realiza a abertura, consulta e solicitação de consolidação dos
 * protocolos de envio.</p>
 *
 * <p>Após a abertura do protocolo, as entidades de negócio são transmitidas
 * pelos clientes especializados de cada módulo, como
 * {@link SagresContabilidadeClient}.</p>
 */
@Component
public class SagresEnvioClient extends AbstractTceRestClient {

    private final TceEnvioMapper mapper;
    private final TceValidacaoMapper validacaoMapper;

    public SagresEnvioClient(AccessTokenService accessTokenService,
                             SagresProperties tceSagresProperties,
                             SagresErrorHandler errorHandler,
                             TceEnvioMapper mapper,
                             TceValidacaoMapper validacaoMapper) {

        super(accessTokenService, tceSagresProperties, errorHandler);

        this.mapper = mapper;
        this.validacaoMapper = validacaoMapper;
    }

    /**
     * Abre um novo protocolo de envio no SAGRES.
     */
    public SagresEnvioResult criarEnvio(
            IntegrationContext<?, ?> context,
            SagresTipoEnvio tipoEnvio) {

        SagresEnvioRequest request = criarRequest(context, tipoEnvio);

        String uri = SagresUris.envios();

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.CRIAR_ENVIO,
                HttpMethod.POST,
                uri,
                context,
                request);

        registrarRequisicao(logContext);

        try {

            SagresEnvioResponse response =
                    restClient(context)
                            .post()
                            .uri(uri)
                            .body(request)
                            .retrieve()
                            .body(SagresEnvioResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(logContext, response);

        } catch (RestClientResponseException ex) {

            return SagresEnvioResult.builder()
                    .result(criarResultadoErro(logContext, ex))
                    .build();
        }
    }

    /**
     * Consulta um envio pelo protocolo.
     */
    public SagresEnvioResult consultarEnvioPorProtocolo(IntegrationContext<?, ?> context,
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

            SagresEnvioResponse response =
                    restClient(context)
                            .get()
                            .uri(uri)
                            .retrieve()
                            .body(SagresEnvioResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(logContext, response);

        } catch (RestClientResponseException ex) {

            return SagresEnvioResult.builder()
                    .result(criarResultadoErro(logContext, ex))
                    .build();
        }
    }

    /**
     * Consulta um envio pelo código da Unidade Gestora,
     * tipo de envio e competência.
     */
    public SagresEnvioResult consultarEnvioPorUnidadeGestoraTipoCompetencia(
            IntegrationContext<?, ?> context,
            String codigoUnidadeGestora,
            SagresTipoEnvio tipoEnvio,
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

            SagresEnvioResponse response =
                    restClient(context)
                            .get()
                            .uri(uri)
                            .retrieve()
                            .body(SagresEnvioResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(logContext, response);

        } catch (RestClientResponseException ex) {

            return SagresEnvioResult.builder()
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
    public SagresValidacaoResult consultarValidacoes(IntegrationContext<?, ?> context,
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

            SagresValidacaoResponse response =
                    restClient(context)
                            .get()
                            .uri(uri)
                            .retrieve()
                            .body(SagresValidacaoResponse.class);

            registrarSucesso(logContext, response);

            return criarResultadoSucesso(logContext, response);

        } catch (RestClientResponseException ex) {

            return SagresValidacaoResult.builder()
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
    public SagresEnvio consolidar(IntegrationContext<?, ?> context, SagresEnvio envio) {

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

            SagresErrorResponse error =
                    errorHandler.parse(ex);

            registrarErro(logContext, error, ex);
            throw ex;
        }
    }

    /**
     * Deletar um envio pelo protocolo e todos os dados associados.
     */
    public SagresEnvioResult deletarEnvioPorProtocolo(IntegrationContext<?, ?> context,
                                                        String protocoloEnvio) {

        String uri = SagresUris.envio(protocoloEnvio);

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.DELETAR_ENVIO_PROTOCOLO,
                HttpMethod.DELETE,
                uri,
                context,
                null);

        logContext.setProtocolo(protocoloEnvio);

        registrarRequisicao(logContext);

        try {

            restClient(context)
                    .delete()
                    .uri(uri)
                    .retrieve()
                    .toBodilessEntity();

            registrarSucesso(logContext, null);

            return criarResultadoSucesso(logContext);

        } catch (RestClientResponseException ex) {

            return SagresEnvioResult.builder()
                    .result(criarResultadoErro(logContext, ex))
                    .build();
        }
    }



    /**
     * Cria o payload de abertura do protocolo.
     */
    private SagresEnvioRequest criarRequest(IntegrationContext<?, ?> context, SagresTipoEnvio tipoEnvio) {

        return SagresEnvioRequest.builder()
                .tipoEnvio(tipoEnvio)
                .competencia(
                        context.getExercicio())
                .build();
    }

    /**
     * Cria um resultado de sucesso a partir da resposta do SAGRES.
     */
    private SagresEnvioResult criarResultadoSucesso(
            SagresLogContext logContext,
            SagresEnvioResponse response) {

        SagresEnvioResult result = SagresEnvioResult.builder()
                .envio(mapper.toModel(response))
                .result(sucesso())
                .build();

        registrarResultado(logContext, result.getResult());
        return result;
    }


    private SagresValidacaoResult criarResultadoSucesso(
            SagresLogContext logContext,
            SagresValidacaoResponse response) {

        SagresValidacaoResult  result = SagresValidacaoResult.builder()
                .validacao(validacaoMapper.toModel(response))
                .result(sucesso())
                .build();

        registrarResultado(logContext, result.getResult());
        return result;
    }

    private SagresEnvioResult criarResultadoSucesso(SagresLogContext logContext) {

        SagresEnvioResult result =
                SagresEnvioResult.builder()
                        .result(sucesso())
                        .build();

        registrarResultado(logContext, result.getResult());

        return result;
    }

    /**
     * Registra o erro retornado pelo SAGRES e cria o resultado
     * base da integração.
     */
    private IntegrationResult criarResultadoErro(
            SagresLogContext logContext,
            RestClientResponseException ex) {

        SagresErrorResponse error =
                errorHandler.parse(ex);

        registrarErro(logContext, error, ex);

        return errorHandler.handle(ex);
    }
}