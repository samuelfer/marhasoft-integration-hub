package br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimentoPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de abastecimento enviados na integração.
     */
    @Builder.Default
    private List<AbastecimentoItemPayload> elementos = new ArrayList<>();
}
