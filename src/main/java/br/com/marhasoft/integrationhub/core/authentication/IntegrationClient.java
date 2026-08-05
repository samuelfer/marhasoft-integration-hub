package br.com.marhasoft.integrationhub.core.authentication;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationClient {

    /**
     * Nome do client OAuth configurado na biblioteca.
     */
    private String client;

    /**
     * Chave de integração do cliente no Hub.
     */
    private String integrationKey;

}