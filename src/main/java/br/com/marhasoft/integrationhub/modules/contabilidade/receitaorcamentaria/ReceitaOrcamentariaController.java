package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria;

import br.com.marhasoft.integrationhub.core.AbstractIntegrationController;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.sagres.validation.SagresFormatValidator;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaBatchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


/**
 * Endpoint responsável pelo envio de Receitas Orçamentárias ao SAGRES.
 *
 * <p>Recebe um lote de receitas orçamentárias, executa as validações
 * necessárias e inicia o fluxo de integração com o SAGRES.</p>
 */
@RestController
@RequestMapping("/api/v1/contabilidade/receita-orcamentaria")
@RequiredArgsConstructor
public class ReceitaOrcamentariaController extends AbstractIntegrationController {

    private final ReceitaOrcamentariaService service;

    /**
     * Envia um lote de Receitas Orçamentárias ao SAGRES.
     */
    @PostMapping
    public ResponseEntity<IntegrationResult> create(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @RequestHeader("X-Exercise") LocalDate competencia,
            @Valid @RequestBody ReceitaOrcamentariaBatchRequest request) {

        validateIntegrationKey(integrationKey);

        IntegrationResult result =
                service.create(request, configuration(competencia), integrationClient());

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Envia um lote de Receitas Orçamentárias para um protocolo
     * de envio já existente no SAGRES.
     *
     * <p>Este endpoint é utilizado para retomar uma integração
     * interrompida após a criação do protocolo, evitando a abertura
     * de um novo protocolo de envio.</p>
     */
    @PostMapping("/{protocolo}")
    public ResponseEntity<IntegrationResult> enviarParaProtocolo(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @PathVariable String protocolo,
            @Valid @RequestBody ReceitaOrcamentariaBatchRequest request) {

        validateIntegrationKey(integrationKey);
        SagresFormatValidator.validateProtocoloEnvio(protocolo);

        IntegrationResult result = service.enviarParaProtocolo(protocolo,
                        request, configuration(), integrationClient());

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

}
