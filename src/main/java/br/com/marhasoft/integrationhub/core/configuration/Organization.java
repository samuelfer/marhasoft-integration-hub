package br.com.marhasoft.integrationhub.core.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Organization {

    private Long id;

    private String nome;

    private String codigo;

    private String cnpj;

    private String razaoSocial;
}