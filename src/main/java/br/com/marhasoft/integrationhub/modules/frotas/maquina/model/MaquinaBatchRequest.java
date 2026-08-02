package br.com.marhasoft.integrationhub.modules.frotas.maquina.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MaquinaBatchRequest implements BatchRequest<MaquinaRequest> {

    @NotEmpty(message = "É necessário informar pelo menos uma máquina.")
    @Valid
    private List<MaquinaRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}