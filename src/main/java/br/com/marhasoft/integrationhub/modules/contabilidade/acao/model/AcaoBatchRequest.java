package br.com.marhasoft.integrationhub.modules.contabilidade.acao.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AcaoBatchRequest implements BatchRequest<AcaoRequest> {

    @NotEmpty(message = "É necessário informar pelo menos uma ação.")
    @Valid
    private List<AcaoRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}