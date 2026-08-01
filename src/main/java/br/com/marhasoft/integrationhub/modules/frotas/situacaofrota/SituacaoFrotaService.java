package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SituacaoFrotaService {

    private final SituacaoFrotaConnector connector;
    private final IntegrationExecutor executor;

    public IntegrationResult create(SituacaoFrotaBatchRequest request,
                                    IntegrationConfiguration configuration) {
        return executor.execute(connector, request, configuration);
    }
}