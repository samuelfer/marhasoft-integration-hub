package br.com.marhasoft.integrationhub.modules.frotas.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de veículos enviados na integração.
     */
    @Builder.Default
    private List<VeiculoItemPayload> elementos = new ArrayList<>();
}
