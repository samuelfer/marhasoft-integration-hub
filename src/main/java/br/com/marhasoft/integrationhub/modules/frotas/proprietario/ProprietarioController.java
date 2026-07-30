package br.com.marhasoft.integrationhub.modules.frotas.proprietario;

import br.com.marhasoft.integrationhub.core.configuration.EnvironmentType;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.configuration.Organization;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioBatchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
 * Arquitetura prevista:
 *
 * ERP
 *   |
 *   | HTTP
 *   | X-IntegrationHub-Key
 *   v
 * Integration Hub
 *   |
 *   | Consulta a chave na base de dados
 *   |
 *   +--> Cliente
 *           |
 *           +--> Organização
 *           +--> Ambiente
 *           +--> Credenciais
 *   |
 *   v
 * IntegrationConfiguration
 *   |
 *   v
 * IntegrationExecutor
 *
 * Dessa forma, a aplicação cliente envia apenas sua chave de integração,
 * enquanto todas as configurações necessárias são resolvidas internamente
 * pelo Integration Hub.
 */
@RestController
@RequestMapping("/api/v1/frotas/proprietarios")
@RequiredArgsConstructor
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    @PostMapping
    public ResponseEntity<IntegrationResult> create(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @RequestHeader("X-Exercise") Integer exercise,
            @Valid @RequestBody ProprietarioBatchRequest request) {

        validateIntegrationKey(integrationKey);

        // TODO (Integração de Clientes):
        // Atualmente a configuração da integração é montada utilizando valores
        // fixos para facilitar o desenvolvimento.
        //
        // Em uma implementação futura, a IntegrationConfiguration deverá ser
        // construída a partir das informações do cliente identificado pela
        // X-IntegrationHub-Key, consultando a base de dados do Integration Hub.
        IntegrationConfiguration configuration =
                IntegrationConfiguration.builder()
                        .organization(temporaryOrganization())
                        .environment(EnvironmentType.HOMOLOGATION)
                        .exercicio(exercise)
                        .build();

        IntegrationResult result =
                proprietarioService.create(request, configuration);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    // TODO (Integração de Clientes):
    /**
     * Valida a chave de integração enviada pelo cliente.
     *
     * <p>Por enquanto a validação é realizada utilizando uma chave fixa definida
     * na aplicação.</p>
     *
     * <p>Futuramente esta validação deverá consultar a base de dados do
     * Integration Hub para verificar:
     * <ul>
     *   <li>se a chave existe;</li>
     *   <li>se o cliente está ativo;</li>
     *   <li>qual organização está associada à chave;</li>
     *   <li>qual ambiente deverá ser utilizado na integração.</li>
     * </ul>
     * </p>
     *
     * @param integrationKey chave enviada no cabeçalho HTTP.
     */
    private void validateIntegrationKey(String integrationKey) {
        if (!"MARHASOFT-DEV".equals(integrationKey)) {
            throw new IllegalArgumentException("Chave de integração inválida.");
        }
    }

    // TODO (Integração de Clientes):
    /**
     * Retorna a organização utilizada durante o desenvolvimento.
     *
     * <p>Esta implementação é temporária e existe apenas para permitir o
     * funcionamento do Integration Hub sem uma base de dados de clientes.</p>
     *
     * <p>Futuramente a organização deverá ser obtida a partir da chave de
     * integração informada no cabeçalho HTTP (X-IntegrationHub-Key), permitindo
     * identificar automaticamente o cliente que está consumindo a API.</p>
     *
     * <p>Nesse momento este método deverá ser removido.</p>
     */
    private Organization temporaryOrganization() {
        return Organization.builder()
                .codigo("1001")
                .nome("Organização Padrão")
                .cnpj("00000000000191")
                .build();
    }

}
