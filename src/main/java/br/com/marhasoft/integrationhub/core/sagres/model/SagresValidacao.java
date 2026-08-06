package br.com.marhasoft.integrationhub.core.sagres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagresValidacao {

    private String protocoloEnvio;

    private LocalDate competencia;

    private String chaveValidacao;

    private String statusValidacao;

    private SagresValidacaoDetalhes detalhes;
}