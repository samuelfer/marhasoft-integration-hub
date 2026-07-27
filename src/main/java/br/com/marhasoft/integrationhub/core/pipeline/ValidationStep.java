package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationError;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;

public class ValidationStep<T, R> extends AbstractPipelineStep<T, R> {

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

        ValidationResult validation = context.getConnector().validate(context);

        if (validation == null) {
            throw new IllegalStateException(
                    "O conector retornou um ValidationResult nulo.");
        }

        if (validation.isValid()) {
            return;
        }

        addValidationErrors(context, validation);
    }

    private void addValidationErrors(
            IntegrationContext<T, R> context,
            ValidationResult validation) {

        validation.getErrors().forEach(error ->
                context.getResult().addError(
                        error.code(),
                        error.message()));
    }
}