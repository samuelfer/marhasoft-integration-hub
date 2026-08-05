package br.com.marhasoft.integrationhub.core.tce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TceEnvioRequest {

    /**
     * Tipo do envio.
     */
    private TipoEnvio tipoEnvio;

    /**
     * Competência do envio.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate competencia;

}