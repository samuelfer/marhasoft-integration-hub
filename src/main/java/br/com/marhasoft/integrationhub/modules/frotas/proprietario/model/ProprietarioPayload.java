package br.com.marhasoft.integrationhub.modules.frotas.proprietario.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProprietarioPayload {

    /**
     * Data e hora utilizada pelo TCE para ordenar o processamento dos payloads.
     */
    private LocalDateTime timestamp;

    /**
     * Lista de proprietarios enviados na integração.
     */
    @Builder.Default
    private List<ProprietarioItemPayload> elementos = new ArrayList<>();
}
