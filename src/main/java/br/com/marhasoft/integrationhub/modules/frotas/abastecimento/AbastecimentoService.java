package br.com.marhasoft.integrationhub.modules.frotas.abastecimento;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model.AbastecimentoBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbastecimentoService {

    private final AbastecimentoConnector connector;
    private final IntegrationExecutor executor;

    public IntegrationResult create(AbastecimentoBatchRequest request,
                                    IntegrationConfiguration configuration) {
        return executor.execute(connector, request, configuration);
    }
}