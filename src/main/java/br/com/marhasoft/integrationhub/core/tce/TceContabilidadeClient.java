package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.result.IntegrationStatus;
import br.com.marhasoft.integrationhub.modules.contabilidade.EntidadeContabilidadeEnum;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaPayload;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TceContabilidadeClient {

    private final OAuthRestClientFactory factory;
    private final TceErrorHandler errorHandler;

    public IntegrationResult cadastrarReceitaOrcamentaria(
            IntegrationContext<?, ?> context,
            ReceitaOrcamentariaPayload payload) {

        return enviar(
                context,
                EntidadeContabilidadeEnum.RECEITAS_ORCAMENTARIAS,
                payload);
    }

    public IntegrationResult cadastrarAcao(
            IntegrationContext<?, ?> context,
            AcaoPayload payload) {

        return enviar(
                context,
                EntidadeContabilidadeEnum.ACOES,
                payload);
    }

    public IntegrationResult cadastrarAtualizacaoOrcamentaria(
            IntegrationContext<?, ?> context,
            AtualizacaoOrcamentariaPayload payload) {

        return enviar(
                context,
                EntidadeContabilidadeEnum.ATUALIZACOES_ORCAMENTARIAS,
                payload);
    }

    public IntegrationResult cadastrarContaBancaria(
            IntegrationContext<?, ?> context,
            ContaBancariaPayload payload) {

        return enviar(
                context,
                EntidadeContabilidadeEnum.CONTAS_BANCARIAS,
                payload);
    }

    public IntegrationResult cadastrarConciliacaoBancaria(
            IntegrationContext<?, ?> context,
            ConciliacaoBancariaPayload payload) {

        return enviar(
                context,
                EntidadeContabilidadeEnum.CONCILIACOES_BANCARIAS,
                payload);
    }

    /**
     * Envia uma entidade da Contabilidade ao TCE.
     */
    private IntegrationResult enviar(
            IntegrationContext<?, ?> context,
            EntidadeContabilidadeEnum entidade,
            Object payload) {

        log.info(
                "Enviando entidade '{}' para o protocolo '{}'.",
                entidade,
                context.getEnvio().getProtocoloEnvio());

        try {

            restClient(context)
                    .post()
                    .uri(
                            "/envios/{protocolo}/entidades/{entidade}",
                            context.getEnvio().getProtocoloEnvio(),
                            entidade.getEndpoint())
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();

            log.info(
                    "Entidade '{}' enviada com sucesso para o protocolo '{}'.",
                    entidade,
                    context.getEnvio().getProtocoloEnvio());

            return sucesso();

        } catch (RestClientResponseException ex) {

            log.error(
                    "Erro ao enviar entidade '{}' para o protocolo '{}'. Status: {}. Resposta: {}",
                    entidade,
                    context.getEnvio().getProtocoloEnvio(),
                    ex.getStatusCode(),
                    ex.getResponseBodyAsString());

            return errorHandler.handle(ex);
        }
    }

    /**
     * Obtém o RestClient autenticado.
     */
    private RestClient restClient(
            IntegrationContext<?, ?> context) {

        return factory.create(
                context.getAuthentication().getClient());
    }

    /**
     * Cria um resultado de sucesso.
     */
    private IntegrationResult sucesso() {

        IntegrationResult result = new IntegrationResult();
        result.setStatus(IntegrationStatus.SUCCESS);

        return result;
    }
}