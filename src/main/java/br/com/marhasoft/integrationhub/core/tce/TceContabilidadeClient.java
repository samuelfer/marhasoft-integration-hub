package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.integration.IntegrationOperationEnum;
import br.com.marhasoft.integrationhub.core.integration.sagres.SagresLogContext;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.EntidadeContabilidadeEnum;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaPayload;
import br.com.marhasoft.oauth.client.api.AccessTokenService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;


/**
 * Cliente responsável pelo envio das entidades do módulo de
 * Contabilidade para um protocolo previamente aberto no SAGRES.
 *
 * <p>As operações deste cliente pressupõem que o protocolo de envio
 * já tenha sido criado pelo {@link TceEnvioClient}.</p>
 *
 * <p>Enquanto o {@link TceEnvioClient} gerencia o ciclo de vida do
 * protocolo (abertura, consulta e consolidação), esta classe é
 * responsável exclusivamente pela transmissão das entidades de
 * Contabilidade para esse protocolo.</p>
 */
@Component
public class TceContabilidadeClient extends AbstractTceRestClient {

    public TceContabilidadeClient(AccessTokenService accessTokenService,
            TceSagresProperties tceSagresProperties, TceErrorHandler errorHandler) {

        super(accessTokenService, tceSagresProperties, errorHandler);
    }

    public IntegrationResult cadastrarReceitaOrcamentaria(IntegrationContext<?, ?> context,
            ReceitaOrcamentariaPayload payload) {

        return enviar(context, EntidadeContabilidadeEnum.RECEITAS_ORCAMENTARIAS,
                payload);
    }

    public IntegrationResult cadastrarAcao(IntegrationContext<?, ?> context, AcaoPayload payload) {

        return enviar(context, EntidadeContabilidadeEnum.ACOES,
                payload);
    }

    public IntegrationResult cadastrarAtualizacaoOrcamentaria(IntegrationContext<?, ?> context,
            AtualizacaoOrcamentariaPayload payload) {

        return enviar(context, EntidadeContabilidadeEnum.ATUALIZACOES_ORCAMENTARIAS,
                payload);
    }

    public IntegrationResult cadastrarContaBancaria(IntegrationContext<?, ?> context,
            ContaBancariaPayload payload) {

        return enviar(context, EntidadeContabilidadeEnum.CONTAS_BANCARIAS,
                payload);
    }

    public IntegrationResult cadastrarConciliacaoBancaria(IntegrationContext<?, ?> context,
            ConciliacaoBancariaPayload payload) {

        return enviar(context, EntidadeContabilidadeEnum.CONCILIACOES_BANCARIAS,
                payload);
    }

    /**
     * Método genérico utilizado por todas as operações de envio de entidades
     * do módulo de Contabilidade.
     *
     * <p>Envia uma entidade para um protocolo previamente aberto no SAGRES,
     * concentrando toda a comunicação com o endpoint de entidades, incluindo
     * autenticação, registro de logs, tratamento de erros e retorno do
     * resultado da integração.</p>
     *
     * <p>Cada chamada envia exatamente uma entidade para o protocolo
     * informado.</p>
     *
     * @param context contexto da integração
     * @param entidade tipo da entidade que será enviada
     * @param payload dados da entidade
     * @return resultado da integração
     */
    private IntegrationResult enviar(IntegrationContext<?, ?> context,
            EntidadeContabilidadeEnum entidade, Object payload) {

        String uri =
                SagresUris.entidade(
                        context.getEnvio().getProtocoloEnvio(),
                        entidade.getEndpoint());

        SagresLogContext logContext = criarLogContext(
                IntegrationOperationEnum.ENVIAR_ENTIDADE,
                HttpMethod.POST, uri, context, payload);

        logContext.setProtocolo(
                context.getEnvio().getProtocoloEnvio());

        registrarRequisicao(logContext);

        try {

            restClient(context)
                    .post()
                    .uri(uri)
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();

            registrarSucesso(logContext);

            return sucesso();

        } catch (RestClientResponseException ex) {

            return registrarErroECriarResultado(logContext, ex);
        }
    }

    /**
     * Registra o erro retornado pelo SAGRES e cria o resultado
     * correspondente da integração.
     */
    private IntegrationResult registrarErroECriarResultado(SagresLogContext logContext,
            RestClientResponseException ex) {

        registrarErro(logContext, ex);

        return errorHandler.handle(ex);
    }
}