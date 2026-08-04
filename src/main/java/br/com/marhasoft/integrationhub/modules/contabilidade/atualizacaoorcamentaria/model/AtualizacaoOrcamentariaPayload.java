package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizacaoOrcamentariaPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de atualizações orçamentárias enviadas na integração.
     */
    @Builder.Default
    private List<AtualizacaoOrcamentariaItemPayload> elementos = new ArrayList<>();
}
