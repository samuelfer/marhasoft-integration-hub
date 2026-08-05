package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.tce.enums.TceValidationCodeEnum;
import br.com.marhasoft.integrationhub.core.tce.model.TceErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class TceErrorHandler {

    private final ObjectMapper objectMapper;

    public IntegrationResult handle(RestClientResponseException ex) {
        IntegrationResult result = new IntegrationResult();

        try {

            TceErrorResponse error = objectMapper.readValue(ex.getResponseBodyAsString(),
                    TceErrorResponse.class);

            result.addError(TceValidationCodeEnum.TCE_ENVIO.name(), error.getDescricao());

        } catch (JsonProcessingException e) {

            result.addError(
                    TceValidationCodeEnum.TCE_ENVIO.name(),
                    ex.getResponseBodyAsString());
        }

        return result;
    }

}