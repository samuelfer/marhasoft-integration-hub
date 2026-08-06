package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria;

import br.com.marhasoft.integrationhub.core.AbstractIntegrationController;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaBatchRequest;
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
@RequestMapping("/api/v1/contabilidade/atualizao-orcamentaria")
@RequiredArgsConstructor
public class AtualizacaoOrcamentariaController extends AbstractIntegrationController {

    private final AtualizacaoOrcamentariaService atualizacaoOrcamentariaService;

    @PostMapping
    public ResponseEntity<IntegrationResult> create(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @RequestHeader("X-Exercise") LocalDate competencia,
            @Valid @RequestBody AtualizacaoOrcamentariaBatchRequest request) {

        validateIntegrationKey(integrationKey);

        IntegrationResult result =
                atualizacaoOrcamentariaService.create(request, configuration(competencia), integrationClient());

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(null);
    }

}
