package br.com.marhasoft.integrationhub.modules.contabilidade.acao.model;

import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcaoRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Ação " + codigoAcao;
    }

    @NotBlank(message = "O código da unidade gestora é obrigatório.")
    @Size(
            min = 6,
            max = 6,
            message = "O código da unidade gestora deve conter 6 dígitos.")
    @Pattern(
            regexp = "\\d+",
            message = "O código da unidade gestora deve conter apenas números.")
    private String codigoUnidadeGestora;

    @NotBlank(message = "O código da ação é obrigatório.")
    @Size(
            min = 4,
            max = 4,
            message = "O código da ação deve conter 4 dígitos.")
    @Pattern(
            regexp = "\\d+",
            message = "O código da ação deve conter apenas números.")
    private String codigoAcao;

    @NotBlank(message = "A descrição da ação é obrigatória.")
    @Size(
            min = 10,
            max = 70,
            message = "A descrição da ação deve conter entre 10 e 70 caracteres.")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A descrição da ação não pode conter apenas espaços.")
    private String descricaoAcao;

    @NotBlank(message = "O tipo da ação é obrigatório.")
    @Size(
            min = 1,
            max = 1,
            message = "O tipo da ação deve conter 1 dígito.")
    @Pattern(
            regexp = "\\d",
            message = "O tipo da ação deve conter apenas números.")
    private String tipoAcao;

    @NotBlank(message = "A descrição da meta é obrigatória.")
    @Size(
            min = 10,
            max = 150,
            message = "A descrição da meta deve conter entre 10 e 150 caracteres.")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A descrição da meta não pode conter apenas espaços.")
    private String descricaoMeta;

    @NotBlank(message = "A unidade de medida é obrigatória.")
    @Size(
            min = 1,
            max = 50,
            message = "A unidade de medida deve conter entre 1 e 50 caracteres.")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A unidade de medida não pode conter apenas espaços.")
    private String unidadeMedida;
}