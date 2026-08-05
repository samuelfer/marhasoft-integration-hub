package br.com.marhasoft.integrationhub.core.tce;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SagresEndpoint {

    ENVIOS("/envios"),
    ENTIDADES("/entidades"),
    CONSOLIDACOES("/consolidacoes");

    private final String path;
}