package br.com.marhasoft.integrationhub.modules.frotas.veiculo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VeiculoBatchRequest {

    @NotEmpty(message = "É necessário informar pelo menos um veículo.")
    @Valid
    private List<VeiculoRequest> elementos;

    private Boolean processamentoAssincrono;

    private String identificadorLote;

    private String observacao;

}