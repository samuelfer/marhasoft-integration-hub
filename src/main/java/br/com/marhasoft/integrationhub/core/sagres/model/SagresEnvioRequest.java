package br.com.marhasoft.integrationhub.core.sagres.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagresEnvioRequest {

    /**
     * Tipo do envio.
     */
    private SagresTipoEnvio tipoEnvio;

    /**
     * Competência do envio.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate competencia;

}