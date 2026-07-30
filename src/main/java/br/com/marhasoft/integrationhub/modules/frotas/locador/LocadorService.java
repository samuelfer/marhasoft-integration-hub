package br.com.marhasoft.integrationhub.modules.frotas.locador;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocadorService {

    private final LocadorConnector connector;
    private final IntegrationExecutor executor;

    public IntegrationResult create(LocadorBatchRequest request,
                                    IntegrationConfiguration configuration) {
        return executor.execute(connector, request, configuration);
    }
}