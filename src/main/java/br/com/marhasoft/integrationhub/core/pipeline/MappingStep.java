package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import org.springframework.stereotype.Component;

@Component
public class MappingStep<T, P> extends AbstractPipelineStep<T, P> {

    @Override
    public PipelinePhase phase() {
        return PipelinePhase.MAPPING;
    }

    @Override
    protected void doExecute(IntegrationContext<T, P> context) {
        context.getConnector().map(context);
    }
}