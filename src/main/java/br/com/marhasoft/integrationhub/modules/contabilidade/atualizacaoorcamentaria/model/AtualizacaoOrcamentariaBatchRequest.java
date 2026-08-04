package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AtualizacaoOrcamentariaBatchRequest implements BatchRequest<AtualizacaoOrcamentariaRequest> {

    @NotEmpty(message = "É necessário informar pelo menos uma atualização orçamentária.")
    @Valid
    private List<AtualizacaoOrcamentariaRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}