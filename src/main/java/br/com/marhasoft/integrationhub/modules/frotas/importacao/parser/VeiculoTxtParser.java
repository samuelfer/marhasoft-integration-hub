package br.com.marhasoft.integrationhub.modules.frotas.importacao.parser;

import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.DocumentoUtils;
import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.FixedWidthUtils;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser da tabela 4.52 - Veiculos.
 *
 * Layout:
 * codUnidadeGestora    1-6
 * placa                7-13
 * anoModelo            14-17
 * numeroRenavan        18-28
 * numero_modelo        29-34
 * tipo_frota           35-35
 * cpfcnpjProprietario  36-49
 * cpfcnpjLocador       50-63 (opcional)
 *
 * A tabela Veiculos não traz o NOME do proprietário/locador, apenas o CPF/CNPJ.
 * Como VeiculoRequest exige nomeProprietario/nomeLocador, este parser recebe os
 * mapas cpfCnpj -> nome, montados a partir das tabelas ProprietarioFrota (4.50)
 * e LocadorPrestador (4.51).
 */
public final class VeiculoTxtParser {

    private VeiculoTxtParser() {
    }

    public static VeiculoRequest parseLinha(String linha,
                                             Map<String, String> nomesPorCpfCnpjProprietario,
                                             Map<String, String> nomesPorCpfCnpjLocador) {

        String placa = FixedWidthUtils.extract(linha, 7, 13);
        String anoModelo = FixedWidthUtils.extract(linha, 14, 17);
        String numeroRenavan = FixedWidthUtils.extract(linha, 18, 28);
        String numeroModelo = FixedWidthUtils.extract(linha, 29, 34);
        String tipoFrota = FixedWidthUtils.extract(linha, 35, 35);

        String cpfCnpjProprietario = DocumentoUtils.normalizarCpfCnpj(
                FixedWidthUtils.extract(linha, 36, 49));

        String cpfCnpjLocadorBruto = FixedWidthUtils.extractOrNull(linha, 50, 63);
        String cpfCnpjLocador = cpfCnpjLocadorBruto == null
                ? null
                : DocumentoUtils.normalizarCpfCnpj(cpfCnpjLocadorBruto);

        String nomeProprietario = nomesPorCpfCnpjProprietario.get(cpfCnpjProprietario);
        String nomeLocador = cpfCnpjLocador == null
                ? null
                : nomesPorCpfCnpjLocador.get(cpfCnpjLocador);

        return VeiculoRequest.builder()
                .placa(placa)
                .anoModelo(anoModelo)
                .numeroRenavan(numeroRenavan)
                .numeroModelo(numeroModelo)
                .tipoFrota(tipoFrota)
                .cpfCnpjProprietario(cpfCnpjProprietario)
                .nomeProprietario(nomeProprietario)
                .cpfCnpjLocador(cpfCnpjLocador)
                .nomeLocador(nomeLocador)
                .build();
    }

    /**
     * Lê o arquivo VEICULOS.txt inteiro, resolvendo os nomes a partir das listas de
     * proprietários e locadores já parseadas (ver ProprietarioFrotaTxtParser / LocadorPrestadorTxtParser).
     */
    public static List<VeiculoRequest> parseArquivo(Path caminho,
                                                      List<ProprietarioRequest> proprietarios,
                                                      List<LocadorRequest> locadores) throws IOException {

        Map<String, String> nomesProprietarios = new HashMap<>();
        for (ProprietarioRequest p : proprietarios) {
            nomesProprietarios.put(p.getCpfCnpj(), p.getNome());
        }

        Map<String, String> nomesLocadores = new HashMap<>();
        for (LocadorRequest l : locadores) {
            nomesLocadores.put(l.getCpfCnpj(), l.getNome());
        }

        List<VeiculoRequest> resultado = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(caminho, StandardCharsets.ISO_8859_1)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) {
                    continue;
                }
                resultado.add(parseLinha(linha, nomesProprietarios, nomesLocadores));
            }
        }
        return resultado;
    }
}
