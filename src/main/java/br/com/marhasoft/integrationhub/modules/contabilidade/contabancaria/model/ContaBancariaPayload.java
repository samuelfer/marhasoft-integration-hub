package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancariaPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de contas bancárias enviadas na integração.
     */
    @Builder.Default
    private List<ContaBancariaItemPayload> elementos = new ArrayList<>();
}
