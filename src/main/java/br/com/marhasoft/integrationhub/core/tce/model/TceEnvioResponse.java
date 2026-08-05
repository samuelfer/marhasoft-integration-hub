package br.com.marhasoft.integrationhub.core.tce.model;

import br.com.marhasoft.integrationhub.core.tce.enums.TceStatusEnvioEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TceEnvioResponse {

    /**
     * Código da unidade gestora.
     */
    private String codigoUnidadeGestora;

    /**
     * Tipo do envio.
     */
    private TipoEnvio tipoEnvio;

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