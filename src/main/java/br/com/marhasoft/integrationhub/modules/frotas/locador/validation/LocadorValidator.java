package br.com.marhasoft.integrationhub.modules.frotas.locador.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocadorValidator {

    public ValidationResult validate(
            LocadorRequest request,
            IntegrationContext<?, ?, ?> context) {

        return ValidationResult.valid();
    }

}