package br.com.marhasoft.integrationhub.core.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegrationConfiguration {

    private Organization organization;

    private EnvironmentType environment = EnvironmentType.PRODUCTION;

    private Integer exercicio;

    private String token;

    private String certificado;
}