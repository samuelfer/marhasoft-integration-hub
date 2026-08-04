package br.com.marhasoft.integrationhub.modules.contabilidade.acao.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcaoPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de ações enviados na integração.
     */
    @Builder.Default
    private List<AcaoItemPayload> elementos = new ArrayList<>();
}
