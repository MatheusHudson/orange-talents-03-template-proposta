package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Validations.CPForCNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PropostaRequest {

    @CPForCNPJ
    @NotBlank
    private String cpfOrCnpj;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;

    @Positive
    @NotNull
    private Double salario;

    public String getCpfOrCnpj() {
        return cpfOrCnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Double getSalario() {
        return salario;
    }

    public Proposta toModel() {
        return new Proposta(cpfOrCnpj, email, nome, endereco, salario);
    }
}
