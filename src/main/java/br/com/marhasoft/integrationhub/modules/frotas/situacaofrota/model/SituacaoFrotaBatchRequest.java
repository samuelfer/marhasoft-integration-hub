package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SituacaoFrotaBatchRequest implements BatchRequest<SituacaoFrotaRequest> {

    @NotEmpty(message = "É necessário informar pelo menos uma situação.")
    @Valid
    private List<SituacaoFrotaRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}