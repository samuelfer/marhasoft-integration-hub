package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public abstract class AbstractPipelineStep<T, P, R>
        implements PipelineStep<T, P, R> {

    @Override
    public final void execute(IntegrationContext<T, P, R> context) {

        before(context);

        doExecute(context);

        after(context);
    }

    protected void before(IntegrationContext<T, P, R> context) {
    }

    protected void after(IntegrationContext<T, P, R> context) {
    }

    protected abstract void doExecute(IntegrationContext<T, P, R> context);

}