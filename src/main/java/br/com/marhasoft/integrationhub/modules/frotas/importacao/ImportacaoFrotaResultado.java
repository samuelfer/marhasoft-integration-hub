package br.com.marhasoft.integrationhub.modules.frotas.importacao;

import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.AbastecimentoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Agrega todas as listas extraídas dos arquivos TXT de frota, prontas para
 * serem enviadas para os endpoints correspondentes da sua API.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportacaoFrotaResultado {

    private List<ProprietarioRequest> proprietarios;
    private List<LocadorRequest> locadores;
    private List<VeiculoRequest> veiculos;
    private List<MaquinaRequest> maquinas;
    private List<SituacaoFrotaRequest> situacoesFrota;
    private List<AbastecimentoRequest> abastecimentos;
}
