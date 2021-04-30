package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Validations.CPForCNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GetPropostaRequest {

    @NotBlank
    @CPForCNPJ
    private String documento;

    @NotBlank
    private String nome;

    @NotNull
    private Long id;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }
}
