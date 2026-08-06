package br.com.marhasoft.integrationhub.core.sagres;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.sagres.enums.TceValidationCodeEnum;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class SagresErrorHandler {

    private final ObjectMapper objectMapper;

    public IntegrationResult handle(RestClientResponseException ex) {
        IntegrationResult result = new IntegrationResult();

        try {

            SagresErrorResponse error = objectMapper.readValue(ex.getResponseBodyAsString(),
                    SagresErrorResponse.class);

            result.addError(TceValidationCodeEnum.TCE_ENVIO.name(), error.getDescricao());

        } catch (JsonProcessingException e) {

            result.addError(
                    TceValidationCodeEnum.TCE_ENVIO.name(),
                    ex.getResponseBodyAsString());
        }

        return result;
    }

    public SagresErrorResponse parse(RestClientResponseException ex) {

        try {

            return objectMapper.readValue(
                    ex.getResponseBodyAsString(),
                    SagresErrorResponse.class);

        } catch (JsonProcessingException e) {

            return SagresErrorResponse.builder()
                    .mensagem(ex.getStatusText())
                    .descricao(ex.getResponseBodyAsString())
                    .build();
        }
    }

}