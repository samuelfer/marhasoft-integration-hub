package br.com.marhasoft.integrationhub.modules.frotas.proprietario.model;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProprietarioBatchRequest implements BatchRequest<ProprietarioRequest> {

    @NotEmpty(message = "É necessário informar pelo menos um proprietário.")
    @Valid
    private List<ProprietarioRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}