package br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.AbastecimentoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AbastecimentoBatchRequest implements BatchRequest<AbastecimentoRequest> {

    @NotEmpty(message = "É necessário informar pelo menos um abastecimento.")
    @Valid
    private List<AbastecimentoRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}