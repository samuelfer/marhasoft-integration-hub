package br.com.marhasoft.integrationhub.modules.frotas.proprietario.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProprietarioValidator {

    public ValidationResult validate(
            ProprietarioRequest request,
            IntegrationContext<?, ?> context) {

        return ValidationResult.valid();
    }

}