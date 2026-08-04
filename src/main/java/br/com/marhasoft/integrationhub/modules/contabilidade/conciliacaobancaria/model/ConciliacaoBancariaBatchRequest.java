package br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConciliacaoBancariaBatchRequest implements BatchRequest<ConciliacaoBancariaRequest> {

    @NotEmpty(message = "É necessário informar pelo menos uma conciliação bancária.")
    @Valid
    private List<ConciliacaoBancariaRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}