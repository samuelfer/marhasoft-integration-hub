package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationError;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;

public class ValidationStep<T, P, R> extends AbstractPipelineStep<T, P, R> {

    @Override
    public PipelinePhase phase() {
        return PipelinePhase.VALIDATION;
    }

    /**
     * Executa a validação da integração e adiciona ao resultado do contexto
     * todos os erros encontrados, caso existam.
     */
    @Override
    protected void doExecute(IntegrationContext<T, P, R> context) {

        ValidationResult validation = context.getConnector().validate(context);

        if (validation == null) {
            throw new IllegalStateException(
                    "O conector retornou um ValidationResult nulo.");
        }

        context.getResult().addErrors(validation.getErrors());
    }
}