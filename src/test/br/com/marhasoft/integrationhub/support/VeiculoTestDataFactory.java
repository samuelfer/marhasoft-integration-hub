package br.com.marhasoft.integrationhub.support;

import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;

public final class VeiculoTestDataFactory {

    private VeiculoTestDataFactory() {
    }

    public static VeiculoRequest umVeiculo() {
        return VeiculoRequest.builder()
                .placa("ABC1234")
                .anoModelo("2025")
                .numeroRenavan("123456789")
                .numeroModelo("987")
                .tipoFrota("1")
                .cpfCnpjProprietario("11111111111")
                .cpfCnpjLocador("22222222222")
                .build();
    }
}