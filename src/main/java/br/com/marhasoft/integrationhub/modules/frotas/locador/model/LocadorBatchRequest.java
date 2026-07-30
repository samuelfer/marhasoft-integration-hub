package br.com.marhasoft.integrationhub.modules.frotas.locador.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LocadorBatchRequest implements BatchRequest<LocadorRequest> {

    @NotEmpty(message = "É necessário informar pelo menos um locador.")
    @Valid
    private List<LocadorRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}