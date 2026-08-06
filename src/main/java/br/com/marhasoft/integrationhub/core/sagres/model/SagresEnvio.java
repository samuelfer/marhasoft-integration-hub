package br.com.marhasoft.integrationhub.core.sagres.model;

import br.com.marhasoft.integrationhub.core.sagres.enums.TceStatusEnvioEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagresEnvio {

    /**
     * Código da unidade gestora.
     */
    private String codigoUnidadeGestora;

    /**
     * Tipo do envio.
     */
    private SagresTipoEnvio tipoEnvio;

    /**
     * Competência do envio.
     */
    private LocalDate competencia;

    /**
     * Status atual do envio.
     */
    private TceStatusEnvioEnum statusEnvio;

    /**
     * Protocolo do envio.
     */
    private String protocoloEnvio;

//    /**
//     * Chave validação do envio.
//     */
//    private String chaveValidacao;
}