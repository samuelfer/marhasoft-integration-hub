package br.com.marhasoft.integrationhub.modules.frotas;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.result.IntegrationStatus;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaPayload;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model.AbastecimentoPayload;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorPayload;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaPayload;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioPayload;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaPayload;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoPayload;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation.VeiculoValidationCode;
import org.springframework.stereotype.Component;

@Component
public class TceFrotasClient {

    /**
     * Envia o payload de veículo ao sistema externo.
     *
     * @param payload payload já convertido para o formato esperado pelo TCE.
     * @return resposta da integração.
     */
    public IntegrationResult cadastrarVeiculo(VeiculoPayload payload) {
        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.ERROR);

        result.addError(
                VeiculoValidationCode.VEICULO_PROPRIETARIO_CADASTRADO.name(),
                "O proprietário informado não está cadastrado no TCE.");

        return result;
    }

    public IntegrationResult cadastrarProprietario(ProprietarioPayload payload) {

        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.SUCCESS);

        result.addInfo(
                "LOCADOR_CADASTRADO",
                "Locador cadastrado com sucesso.");

        return result;
    }

    public IntegrationResult cadastrarLocador(LocadorPayload payload) {

        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.SUCCESS);

        result.addInfo(
                "PROPRIETARIO_CADASTRADO",
                "Proprietário cadastrado com sucesso.");

        return result;

        //simular erro
//        IntegrationResult result = new IntegrationResult();
//
//        result.setStatus(IntegrationStatus.ERROR);
//
//        result.addError(
//                LocadorValidationCode.LOCADOR_PRESTADOR_DUPLICIDADE_NAO_PERMITIDA.name(),
//                "Locador já cadastrado.");
//
//        return result;
    }

    public IntegrationResult cadastrarAbastecimento(AbastecimentoPayload mappedPayload) {

        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.ERROR);

        return result;
    }

    public IntegrationResult cadastrarSituacaoFrota(SituacaoFrotaPayload mappedPayload) {

        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.ERROR);

        return result;
    }

    public IntegrationResult cadastrarMaquina(MaquinaPayload mappedPayload) {
        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.ERROR);

        return result;
    }

    public IntegrationResult cadastrarAcao(AcaoPayload mappedPayload) {
        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.ERROR);

        return result;
    }

    public IntegrationResult cadastrarAtualizacaoOrcamentaria(AtualizacaoOrcamentariaPayload mappedPayload) {
        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.ERROR);

        return result;
    }

    public IntegrationResult cadastrarConciliacaoBancaria(ConciliacaoBancariaPayload mappedPayload) {
        IntegrationResult result = new IntegrationResult();

        result.setStatus(IntegrationStatus.ERROR);

        return result;
    }
}
