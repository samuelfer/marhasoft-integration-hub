package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria;


import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.sagres.*;
import br.com.marhasoft.integrationhub.core.sagres.model.*;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.validation.ReceitaOrcamentariaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ReceitaOrcamentariaConnector implements IntegrationConnector<ReceitaOrcamentariaBatchRequest, ReceitaOrcamentariaPayload> {

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.CONTABILIDADE,
                    IntegrationOperation.RECEITA_ORCAMENTARIA,
                    IntegrationAction.CREATE);

    private final ReceitaOrcamentariaMapper mapper;
    private final ReceitaOrcamentariaValidator validator;
    private final SagresContabilidadeClient client;
    private final SagresEnvioClient envioClient;

    /**
     * Retorna os metadados que identificam esta integração.
     */
    @Override
    public ConnectorMetadata getMetadata() {
        return METADATA;
    }

    /**
     * Valida a requisição antes da execução da integração.
     */
    @Override
    public ValidationResult validate(IntegrationContext<ReceitaOrcamentariaBatchRequest,
            ReceitaOrcamentariaPayload> context) {
        return validateReceitas(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<ReceitaOrcamentariaBatchRequest, ReceitaOrcamentariaPayload> context) {

        context.setMappedPayload(mapper.toPayload(context.getRequest(), getMetadata().action()));
    }

    /**
     * Executa o fluxo de envio da entidade Receita Orçamentária ao SAGRES.
     *
     * <p>O fluxo de comunicação com o TCE ocorre em três etapas:</p>
     *
     * <ol>
     *     <li>Solicita um protocolo de envio (POST /envios);</li>
     *     <li>Envia a entidade utilizando o protocolo recebido
     *         (POST /envios/{protocolo}/entidades/{entidade});</li>
     *     <li>Solicita a consolidação do envio para iniciar o processamento
     *         pelo SAGRES
     *         (POST /envios/{protocolo}/consolidacoes).</li>
     * </ol>
     */
    @Override
    public void send(IntegrationContext<ReceitaOrcamentariaBatchRequest, ReceitaOrcamentariaPayload> context) {
        /*
         * Etapa 1
         * Solicita ao SAGRES a abertura de um protocolo de envio.
         * caso não exista
         */
        if (!abrirProtocolo(context)) {
            return;
        }

        enviarEntidades(context);

        if (!context.getResult().hasErrors()) {
            consolidarProtocolo(context);
        }
    }

    /**
     * Envia as entidades para um protocolo já existente.
     *
     * <p>Este fluxo é utilizado quando o protocolo já foi criado
     * anteriormente, permitindo retomar uma integração interrompida
     * sem a necessidade de abrir um novo protocolo.</p>
     */
    public void enviarParaProtocoloExistente(
            IntegrationContext<ReceitaOrcamentariaBatchRequest,
                    ReceitaOrcamentariaPayload> context) {

        enviarEntidades(context);

        if (!context.getResult().hasErrors()) {
            consolidarProtocolo(context);
        }
    }

    /**
     * Abre um novo protocolo de envio no SAGRES.
     *
     * @return {@code true} caso o protocolo tenha sido criado com sucesso;
     *         {@code false} caso tenha ocorrido algum erro.
     */
    private boolean abrirProtocolo(
            IntegrationContext<ReceitaOrcamentariaBatchRequest,
                    ReceitaOrcamentariaPayload> context) {

        SagresEnvioResult envioResult = envioClient.criarEnvio(
                        context, SagresTipoEnvio.ORCAMENTO);

        context.getResult().merge(envioResult.getResult());

        if (context.getResult().hasErrors()) {
            return false;
        }

        context.setEnvio(envioResult.getEnvio());

        return true;
    }

    /**
     * Envia as entidades da Receita Orçamentária para o protocolo
     * informado no contexto da integração.
     */
    private void enviarEntidades(
            IntegrationContext<ReceitaOrcamentariaBatchRequest,
                    ReceitaOrcamentariaPayload> context) {

        IntegrationResult result = client.cadastrarReceitaOrcamentaria(
                        context, context.getMappedPayload());

        context.getResult().merge(result);
    }

    /**
     * Solicita ao SAGRES a consolidação do protocolo para que
     * as entidades enviadas sejam processadas.
     */
    private void consolidarProtocolo(
            IntegrationContext<ReceitaOrcamentariaBatchRequest,
                    ReceitaOrcamentariaPayload> context) {

        context.setEnvio(envioClient.consolidar(context, context.getEnvio()));
    }

    /**
     * Executa as validações de negócio para todos as ações do lote.
     */
    private ValidationResult validateReceitas(ReceitaOrcamentariaBatchRequest request,
                                           IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        request.getElementos()
                .forEach(receita ->
                        result.merge(
                                validator.validate(
                                        receita,
                                        context)));

        return result;
    }
}