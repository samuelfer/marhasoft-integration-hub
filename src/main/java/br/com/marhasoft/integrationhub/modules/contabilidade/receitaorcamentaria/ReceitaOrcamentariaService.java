package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria;

import br.com.marhasoft.integrationhub.core.authentication.IntegrationClient;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.sagres.SagresEnvioService;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresEnvioResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceitaOrcamentariaService {

    private final ReceitaOrcamentariaConnector connector;
    private final IntegrationExecutor executor;
    private final SagresEnvioService sagresEnvioService;

    public IntegrationResult create(ReceitaOrcamentariaBatchRequest request,
                                    IntegrationConfiguration configuration,
                                    IntegrationClient integrationClient) {
        return executor.execute(connector, request, configuration, integrationClient);
    }

    public IntegrationResult enviarParaProtocolo(
            String protocolo,
            ReceitaOrcamentariaBatchRequest request,
            IntegrationConfiguration configuration,
            IntegrationClient integrationClient) {

        SagresEnvioResult envioResult =
                sagresEnvioService.consultarEnvioPorProtocolo(
                        configuration,
                        integrationClient,
                        protocolo);

        if (envioResult.getResult().hasErrors()) {
            return envioResult.getResult();
        }

        return executor.executeParaProtocolo(
                connector,
                request,
                configuration,
                integrationClient,
                envioResult.getEnvio());
    }
}