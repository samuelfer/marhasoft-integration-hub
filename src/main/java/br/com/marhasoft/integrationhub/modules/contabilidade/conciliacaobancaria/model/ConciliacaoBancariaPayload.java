package br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoBancariaPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de conciliações bancárias enviadas na integração.
     */
    @Builder.Default
    private List<ConciliacaoBancariaItemPayload> elementos = new ArrayList<>();
}
