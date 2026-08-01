package br.com.marhasoft.integrationhub.modules.frotas.abastecimento.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.AbastecimentoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AbastecimentoValidator {

    public ValidationResult validate(
            AbastecimentoRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        // TODO Implementar validação de dependência com SituaçãoFrota.
        // O TCE exige que todo veículo/máquina informado em Abastecimento
        // esteja previamente cadastrado no arquivo SituaçãoFrota.
        // Essa validação deverá utilizar a infraestrutura de resolução de
        // dependências do Integration Hub.

        return result;
    }

}