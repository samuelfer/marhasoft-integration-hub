package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;

public class ValidationStep<T, R>
        extends AbstractPipelineStep<T, R> {

    @Override
    public String name() {
        return "Validation";
    }

    @Override
    public PipelinePhase phase() {
        return PipelinePhase.VALIDATION;
    }

    @Override
    protected void doExecute(IntegrationContext<T, R> context) {

        // implementação
    }
}