package br.com.marhasoft.integrationhub.modules.frotas.importacao.parser;

import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.FixedWidthUtils;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.AbastecimentoRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser da tabela 4.55 - Abastecimento.
 *
 * Layout:
 * codUnidadeGestora  1-6
 * ano                7-10
 * mes                11-12
 * quantidade         13-28  (litros ou m3 abastecidos no mes)
 * tipoCombustivel    29-29
 * categoria          30-30  (1=Veiculo, 2=Maquina)
 * codigo             31-37  (placa do veiculo ou codigo da maquina)
 *
 * Observação: ano/mes de referência não fazem parte de AbastecimentoRequest (provavelmente
 * tratados em outro nível da integração/contexto da requisição), então são apenas
 * extraídos aqui para eventual uso/validação e não entram no objeto final.
 */
public final class AbastecimentoTxtParser {

    private AbastecimentoTxtParser() {
    }

    public static AbastecimentoRequest parseLinha(String linha) {
        String quantidadeBruta = FixedWidthUtils.extract(linha, 13, 28);
        int quantidade = quantidadeBruta.isBlank() ? 0 : Integer.parseInt(quantidadeBruta);

        String tipoCombustivel = FixedWidthUtils.extract(linha, 29, 29);
        String categoria = FixedWidthUtils.extract(linha, 30, 30);
        String codigo = FixedWidthUtils.extract(linha, 31, 37);

        return AbastecimentoRequest.builder()
                .tipoCombustivel(tipoCombustivel)
                .tipoCategoriaFrota(categoria)
                .codigo(codigo)
                .quantidade(quantidade)
                .build();
    }

    /**
     * Lê o arquivo ABASTECIMENTO.txt inteiro e retorna a lista de abastecimentos.
     */
    public static List<AbastecimentoRequest> parseArquivo(Path caminho) throws IOException {
        List<AbastecimentoRequest> resultado = new ArrayList<>();
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
