package br.com.marhasoft.integrationhub.modules.contabilidade.acao;

import br.com.marhasoft.integrationhub.core.AbstractIntegrationController;
import br.com.marhasoft.integrationhub.core.configuration.EnvironmentType;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.configuration.Organization;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoBatchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


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
@RequestMapping("/api/v1/contabilidade/acoes")
@RequiredArgsConstructor
public class AcaoController extends AbstractIntegrationController {

    private final AcaoService acaoService;

    @PostMapping
    public ResponseEntity<IntegrationResult> create(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @RequestHeader("X-Exercise") LocalDate competencia,
            @Valid @RequestBody AcaoBatchRequest request) {

        validateIntegrationKey(integrationKey);

        IntegrationResult result =
                acaoService.create(request, configuration(competencia));

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
