package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria;


import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.validation.ConciliacaoBancariaValidator;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.validation.ContaBancariaValidator;
import br.com.marhasoft.integrationhub.modules.frotas.TceFrotasClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContaBancariaConnector implements IntegrationConnector<ContaBancariaBatchRequest, ContaBancariaPayload> {

    private final IntegrationExecutor executor;

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final ContaBancariaMapper mapper;
    private final ContaBancariaValidator validator;
    private final TceFrotasClient client;

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
    public ValidationResult validate(IntegrationContext<ContaBancariaBatchRequest,
            ContaBancariaPayload> context) {
        return validateAcoes(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<ContaBancariaBatchRequest, ContaBancariaPayload> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(IntegrationContext<ContaBancariaBatchRequest, ContaBancariaPayload> context) {

        context.getResult().merge(
                client.cadastrarContaBancaria(
                        context.getMappedPayload()));
    }

    /**
     * Executa as validações de negócio para todos as ações do lote.
     */
    private ValidationResult validateAcoes(ContaBancariaBatchRequest request,
                                           IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        request.getElementos()
                .forEach(contaBancaria ->
                        result.merge(
                                validator.validate(
                                        contaBancaria,
                                        context)));

        return result;
    }
}