package br.com.marhasoft.integrationhub.core.sagres;

import br.com.marhasoft.integrationhub.core.AbstractIntegrationController;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresEnvioResult;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresTipoEnvio;
import br.com.marhasoft.integrationhub.core.sagres.validation.SagresFormatValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/envios")
@RequiredArgsConstructor
public class SagresEnvioController extends AbstractIntegrationController {

    private final SagresEnvioService service;

    /**
     * Consulta um envio pelo código da Unidade Gestora,
     * tipo de envio e competência.
     */
    @GetMapping("/{codigoUnidadeGestora}/{tipoEnvio}/{competencia}")
    public ResponseEntity<?> consultarPorUnidadeGestoraTipoCompetencia(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @PathVariable String codigoUnidadeGestora,
            @PathVariable SagresTipoEnvio tipoEnvio,
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate competencia) {

        validateIntegrationKey(integrationKey);

        SagresEnvioResult result =
                service.consultarEnvioPorUnidadeGestoraTipoCompetencia(
                        configuration(), integrationClient(), codigoUnidadeGestora,
                        tipoEnvio, competencia);

        if (result.getResult().hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getResult());
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Consulta um envio pelo código da Unidade Gestora,
     * tipo de envio e competência.
     */
    @GetMapping("/{protocolo}")
    public ResponseEntity<?> consultarPorProtocolo(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @PathVariable String protocolo) {

        validateIntegrationKey(integrationKey);
        SagresFormatValidator.validateProtocoloEnvio(protocolo);

        SagresEnvioResult result =
                service.consultarEnvioPorProtocolo(
                        configuration(),
                        integrationClient(),
                        protocolo);

        if (result.getResult().hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getResult());
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Deleta o envio identificado pelo protocolo
     * e todos os dados associados a ele.
     */
    @DeleteMapping("/{protocolo}")
    public ResponseEntity<?> deletar(
            @RequestHeader("X-IntegrationHub-Key") String integrationKey,
            @PathVariable String protocolo) {

        validateIntegrationKey(integrationKey);
        SagresFormatValidator.validateProtocoloEnvio(protocolo);

        SagresEnvioResult result =
                service.deletarProtocolo(
                        configuration(),
                        integrationClient(),
                        protocolo);

        if (result.getResult().hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getResult());
        }

        return ResponseEntity.ok(result);
    }
}