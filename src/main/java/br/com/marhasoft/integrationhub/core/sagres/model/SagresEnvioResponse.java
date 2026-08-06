package br.com.marhasoft.integrationhub.core.sagres.model;

import br.com.marhasoft.integrationhub.core.sagres.enums.TceStatusEnvioEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagresEnvioResponse {

    /**
     * Código da unidade gestora.
     */
    private String codigoUnidadeGestora;

    /**
     * Tipo do envio.
     */
    private SagresTipoEnvio tipoEnvio;

    /**
     * Competência do envio.
     */
    private LocalDate competencia;

    /**
     * Status do envio.
     */
    private TceStatusEnvioEnum statusEnvio;

    /**
     * Protocolo gerado pelo TCE.
     */
    private String protocoloEnvio;

}