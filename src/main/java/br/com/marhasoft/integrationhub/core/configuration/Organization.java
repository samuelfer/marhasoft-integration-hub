package br.com.marhasoft.integrationhub.core.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organization {

    private Long id;

    private String codigo;

    private String cnpj;

    private String razaoSocial;
}