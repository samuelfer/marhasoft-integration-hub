package br.com.marhasoft.integrationhub.core.tce;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "integrationhub.sagres")
@Getter
@Setter
public class TceSagresProperties {

    private String baseUrl;

    // getters e setters
}