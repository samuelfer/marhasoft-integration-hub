package br.com.marhasoft.integrationhub.modules.frotas.importacao;

import br.com.marhasoft.integrationhub.modules.frotas.importacao.parser.*;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.AbastecimentoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Serviço de exemplo que orquestra a leitura dos 6 arquivos TXT do módulo de Frota
 * e monta o {@link ImportacaoFrotaResultado} pronto para envio ao endpoint que
 * valida/salva na API de destino.
*
 * IMPORTANTE: a ordem de leitura importa. Veiculos depende dos nomes já extraídos de
 * ProprietarioFrota e LocadorPrestador, então esses dois arquivos precisam ser lidos antes.
 */
public class TxtFrotaImportService {

    public ImportacaoFrotaResultado importar(
            Path arquivoProprietarioFrota,
            Path arquivoLocadorPrestador,
            Path arquivoVeiculos,
            Path arquivoMaquinas,
            Path arquivoSituacaoFrota,
            Path arquivoAbastecimento
    ) throws IOException {

        List<ProprietarioRequest> proprietarios =
                ProprietarioFrotaTxtParser.parseArquivo(arquivoProprietarioFrota);

        List<LocadorRequest> locadores =
                LocadorPrestadorTxtParser.parseArquivo(arquivoLocadorPrestador);

        List<VeiculoRequest> veiculos =
                VeiculoTxtParser.parseArquivo(arquivoVeiculos, proprietarios, locadores);

        List<MaquinaRequest> maquinas =
                MaquinaTxtParser.parseArquivo(arquivoMaquinas);

        List<SituacaoFrotaRequest> situacoesFrota =
                SituacaoFrotaTxtParser.parseArquivo(arquivoSituacaoFrota);

        List<AbastecimentoRequest> abastecimentos =
                AbastecimentoTxtParser.parseArquivo(arquivoAbastecimento);

        return ImportacaoFrotaResultado.builder()
                .proprietarios(proprietarios)
                .locadores(locadores)
                .veiculos(veiculos)
                .maquinas(maquinas)
                .situacoesFrota(situacoesFrota)
                .abastecimentos(abastecimentos)
                .build();
    }
}
