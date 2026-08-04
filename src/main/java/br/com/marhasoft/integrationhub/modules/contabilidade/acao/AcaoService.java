package br.com.marhasoft.integrationhub.modules.contabilidade.acao;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcaoService {

    private final AcaoConnector connector;
    private final IntegrationExecutor executor;

    public IntegrationResult create(AcaoBatchRequest request,
                                    IntegrationConfiguration configuration) {
        return executor.execute(connector, request, configuration);
    }
}