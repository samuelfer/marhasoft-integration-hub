package br.com.marhasoft.integrationhub.core.sagres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagresValidacaoResponse {

    private String protocoloEnvio;

    private LocalDate competencia;

    private String chaveValidacao;

    private String statusValidacao;

    private SagresValidacaoDetalhesResponse detalhes;
}