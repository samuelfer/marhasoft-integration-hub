package br.com.marhasoft.integrationhub.core;

import br.com.marhasoft.integrationhub.core.authentication.IntegrationClient;
import br.com.marhasoft.integrationhub.core.configuration.EnvironmentType;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.configuration.Organization;
import br.com.marhasoft.integrationhub.exception.InvalidIntegrationKeyException;

import java.time.LocalDate;

/**
 * Classe base para os controllers do Integration Hub.
 *
 * <p>Centraliza funcionalidades comuns utilizadas pelos endpoints da API,
 * evitando duplicação de código entre os controllers.</p>
 *
 * <p>Atualmente esta classe é responsável por:</p>
 * <ul>
 *     <li>validar a chave de integração enviada pelo cliente;</li>
 *     <li>criar o {@link IntegrationClient} utilizado na autenticação
 *         com o sistema externo;</li>
 *     <li>montar a {@link IntegrationConfiguration} da integração;</li>
 *     <li>fornecer uma organização temporária utilizada durante o
 *         desenvolvimento.</li>
 * </ul>
 *
 * <p>Em uma implementação futura, esta classe deverá obter todas essas
 * informações a partir da chave de integração (X-IntegrationHub-Key),
 * consultando a base de dados do Integration Hub para identificar o cliente,
 * suas credenciais, organização, ambiente de execução e demais configurações
 * necessárias para a integração.</p>
 */
public abstract class AbstractIntegrationController {

    protected void validateIntegrationKey(String integrationKey) {

        if (!"MARHASOFT-DEV".equals(integrationKey)) {
            throw new InvalidIntegrationKeyException(
                    "Chave de integração inválida.");
        }
    }

    protected IntegrationClient integrationClient() {

        return IntegrationClient.builder()
                .client("lagoa_de_dentro")
                .build();
    }

    protected IntegrationConfiguration configuration(
            LocalDate exercicio) {

        return IntegrationConfiguration.builder()
                .organization(temporaryOrganization())
                .environment(EnvironmentType.HOMOLOGATION)
                .exercicio(exercicio)
                .build();
    }

    protected IntegrationConfiguration configuration() {

        return IntegrationConfiguration.builder()
                .organization(temporaryOrganization())
                .environment(EnvironmentType.HOMOLOGATION)
                .build();
    }

    private Organization temporaryOrganization() {

        return Organization.builder()
                .codigo("1001")
                .nome("Organização Padrão")
                .cnpj("00000000000191")
                .build();
    }

}