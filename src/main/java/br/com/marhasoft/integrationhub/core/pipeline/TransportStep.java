package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import org.springframework.stereotype.Component;

@Component
public class TransportStep<T, P, R> extends AbstractPipelineStep<T, P, R> {

    @Override
    public PipelinePhase phase() {
        return PipelinePhase.TRANSPORT;
    }

    @Override
    protected void doExecute(IntegrationContext<T, P, R> context) {
        context.getConnector().send(context);
    }
}