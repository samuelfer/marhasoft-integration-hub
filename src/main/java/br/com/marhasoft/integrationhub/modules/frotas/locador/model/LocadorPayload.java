package br.com.marhasoft.integrationhub.modules.frotas.locador.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocadorPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de locadores enviados na integração.
     */
    @Builder.Default
    private List<LocadorItemPayload> elementos = new ArrayList<>();
}
