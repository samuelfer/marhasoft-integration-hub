package br.com.marhasoft.integrationhub.modules.frotas.maquina;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.TceFrotasClient;
import br.com.marhasoft.integrationhub.modules.frotas.locador.LocadorConnector;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaPayload;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.validation.MaquinaValidator;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.ProprietarioConnector;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import static br.com.marhasoft.integrationhub.modules.frotas.maquina.validation.MaquinaValidationCode.MAQUINA_LOCADOR_CADASTRADO;
import static br.com.marhasoft.integrationhub.modules.frotas.maquina.validation.MaquinaValidationCode.MAQUINA_PROPRIETARIO_CADASTRADO;

@Component
@RequiredArgsConstructor
public class MaquinaConnector implements IntegrationConnector<
        MaquinaBatchRequest,
        MaquinaPayload> {

    private final IntegrationExecutor executor;
    private final ProprietarioConnector proprietarioConnector;
    private final LocadorConnector locadorConnector;

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final MaquinaMapper mapper;
    private final MaquinaValidator validator;
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
    public ValidationResult validate(IntegrationContext<MaquinaBatchRequest,
            MaquinaPayload> context) {
        return validateVeiculos(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<MaquinaBatchRequest, MaquinaPayload> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(IntegrationContext<MaquinaBatchRequest, MaquinaPayload> context) {

        IntegrationResult result =
                client.cadastrarMaquina(context.getMappedPayload());

        boolean reenviarVeiculo = false;

        if (result.hasError(MAQUINA_PROPRIETARIO_CADASTRADO)) {

            IntegrationResult proprietarioResult =
                    cadastrarProprietario(context);

            if (proprietarioResult.hasErrors()) {
                context.getResult().merge(proprietarioResult);
                return;
            }

            reenviarVeiculo = true;
        }

        if (result.hasError(MAQUINA_LOCADOR_CADASTRADO)) {

            IntegrationResult locadorResult =
                    cadastrarLocador(context);

            if (locadorResult.hasErrors()) {
                context.getResult().merge(locadorResult);
                return;
            }

            reenviarVeiculo = true;
        }

        if (reenviarVeiculo) {
            result = client.cadastrarMaquina(
                    context.getMappedPayload());
        }

        context.getResult().merge(result);
    }

    /**
     * Executa as validações de negócio para todos as máquinas do lote.
     */
    private ValidationResult validateVeiculos(MaquinaBatchRequest request,
                                              IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        request.getElementos()
                .forEach(veiculo ->
                        result.merge(
                                validator.validate(
                                        veiculo,
                                        context)));

        return result;
    }

    private IntegrationResult cadastrarProprietario(
            IntegrationContext<MaquinaBatchRequest, MaquinaPayload> context) {

        MaquinaRequest maquina = context.getRequest()
                .getElementos()
                .getFirst();

        ProprietarioRequest request = ProprietarioRequest.builder()
                .cpfCnpj(maquina.getCpfCnpjProprietario())
//                .nome(veiculo.getNomeProprietario())
                .build();

//        return executor.execute(
//                proprietarioConnector,
//                ProprietarioBatchRequest.builder()
//                        .elementos(List.of(request))
//                        .build(),
//                context.getConfiguration());
        return null;
    }

    private IntegrationResult cadastrarLocador(
            IntegrationContext<MaquinaBatchRequest, MaquinaPayload> context) {

        MaquinaRequest maquina = context.getRequest()
                .getElementos()
                .getFirst();

        LocadorRequest request = LocadorRequest.builder()
                .cpfCnpj(maquina.getCpfCnpjLocador())
//                .nome(maquina.ge())
                .build();

//        return executor.execute(
//                locadorConnector,
//                LocadorBatchRequest.builder()
//                        .elementos(List.of(request))
//                        .build(),
//                context.getConfiguration());
        return null;
    }
}