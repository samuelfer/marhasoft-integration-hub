package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.BeanValidationService;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

public class ValidationStep<T, P, R> extends AbstractPipelineStep<T, P, R> {

    private final BeanValidationService beanValidationService;

    public ValidationStep(BeanValidationService beanValidationService) {
        this.beanValidationService = beanValidationService;
    }

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

        ValidationResult validation =
                beanValidationService.validate(context.getRequest());

        ValidationResult connectorValidation =
                context.getConnector().validate(context);

        if (connectorValidation == null) {
            throw new IllegalStateException(
                    "O conector retornou um ValidationResult nulo.");
        }

        validation.merge(connectorValidation);

        context.getResult().addErrors(validation.getErrors());
    }
}