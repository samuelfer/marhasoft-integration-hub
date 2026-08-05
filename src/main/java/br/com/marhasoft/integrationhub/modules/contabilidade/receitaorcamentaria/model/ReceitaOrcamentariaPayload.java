package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaOrcamentariaPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento.
     */
    private LocalDateTime timestamp;

    /**
     * Receitas enviadas.
     */
    @Builder.Default
    private List<ReceitaOrcamentariaItemPayload> elementos = new ArrayList<>();
}