package br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria;

import br.com.marhasoft.integrationhub.core.authentication.IntegrationClient;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConciliacaoBancariaService {

    private final ConciliacaoBancariaConnector connector;
    private final IntegrationExecutor executor;

    public IntegrationResult create(ConciliacaoBancariaBatchRequest request,
                                    IntegrationConfiguration configuration,
                                    IntegrationClient integrationClient) {
        return executor.execute(connector, request, configuration, integrationClient);
    }
}