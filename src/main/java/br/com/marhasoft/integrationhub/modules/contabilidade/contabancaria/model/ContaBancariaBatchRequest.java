package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ContaBancariaBatchRequest implements BatchRequest<ContaBancariaRequest> {

    @NotEmpty(message = "É necessário informar pelo menos uma conta bancária.")
    @Valid
    private List<ContaBancariaRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}