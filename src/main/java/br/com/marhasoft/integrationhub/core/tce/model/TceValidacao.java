package br.com.marhasoft.integrationhub.core.tce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TceValidacao {

    private String protocoloEnvio;

    private LocalDate competencia;

    private String chaveValidacao;

    private String statusValidacao;

    private TceValidacaoDetalhes detalhes;
}