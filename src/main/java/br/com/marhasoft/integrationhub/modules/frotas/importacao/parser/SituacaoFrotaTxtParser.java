package br.com.marhasoft.integrationhub.modules.frotas.importacao.parser;

import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.DataUtils;
import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.FixedWidthUtils;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser da tabela 4.54 - SituacaoFrota.
 *
 * Layout:
 * codUnidadeGestora  1-6
 * data               7-14   (DDMMAAAA)
 * tipoSituacao       15-15
 * categoria          16-16  (1=Veiculo, 2=Maquina)
 * codigo             17-23  (placa do veiculo ou codigo da maquina)
 */
public final class SituacaoFrotaTxtParser {

    private SituacaoFrotaTxtParser() {
    }

    public static SituacaoFrotaRequest parseLinha(String linha) {
        String dataBruta = FixedWidthUtils.extract(linha, 7, 14);
        LocalDate dataSituacao = DataUtils.parseDataDDMMAAAA(dataBruta);

        String tipoSituacao = FixedWidthUtils.extract(linha, 15, 15);
        String categoria = FixedWidthUtils.extract(linha, 16, 16);
        String codigo = FixedWidthUtils.extract(linha, 17, 23);

        return SituacaoFrotaRequest.builder()
                .dataSituacao(dataSituacao)
                .tipoSituacao(tipoSituacao)
                .tipoCategoriaFrota(categoria)
                .codigo(codigo)
                .build();
    }

    /**
     * Lê o arquivo SITUACAOFROTA.txt inteiro e retorna a lista de situações.
     */
    public static List<SituacaoFrotaRequest> parseArquivo(Path caminho) throws IOException {
        List<SituacaoFrotaRequest> resultado = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(caminho, StandardCharsets.ISO_8859_1)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) {
                    continue;
                }
                resultado.add(parseLinha(linha));
            }
        }
        return resultado;
    }
}
