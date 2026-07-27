package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public class MappingStep<T, R> extends AbstractPipelineStep<T, R> {

    @Override
    public String name() {
        return "Mapping";
    }

    @Override
    public PipelinePhase phase() {
        return PipelinePhase.MAPPING;
    }

    @Override
    protected void doExecute(IntegrationContext<T, R> context) {
        context.getConnector().map(context);
    }
}