package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Validations.CPForCNPJ;
import br.com.zup.Treinopropostas.Validations.Utils.Resultado;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    public boolean isValid(EntityManager em) {
        Resultado resultado = new Resultado();
        return  !em.createQuery("Select count(p) > 0 From Proposta p WHERE p.cpfOrCnpj = :documento", Boolean.class).setParameter("documento", cpfOrCnpj).getSingleResult();
    }
}
