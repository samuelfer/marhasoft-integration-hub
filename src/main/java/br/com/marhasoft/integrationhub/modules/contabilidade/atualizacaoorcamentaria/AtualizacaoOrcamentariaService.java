package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizacaoOrcamentariaService {

    private final AtualizacaoOrcamentariaConnector connector;
    private final IntegrationExecutor executor;

    public IntegrationResult create(AtualizacaoOrcamentariaBatchRequest request,
                                    IntegrationConfiguration configuration) {
        return executor.execute(connector, request, configuration);
    }
}