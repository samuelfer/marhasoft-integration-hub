package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public class TransportStep<T, R> extends AbstractPipelineStep<T, R> {

    @Override
    public String name() {
        return "Transport";
    }

    @Override
    public PipelinePhase phase() {
        return PipelinePhase.TRANSPORT;
    }

    @Override
    protected void doExecute(IntegrationContext<T, R> context) {
        context.getConnector().send(context);
    }
}