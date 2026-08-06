package br.com.marhasoft.integrationhub.core.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class IntegrationConfiguration {

    private Organization organization;

    private EnvironmentType environment = EnvironmentType.PRODUCTION;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate exercicio;

    private String token;

    private String certificado;
}