package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Cartao.Cartao;
import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PropostaResponse {


    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    private BigDecimal salario;

    @NotBlank
    @NotNull
    private StatusCliente status;

    private Cartao cartao;

    public PropostaResponse(String email, String nome, String endereco, BigDecimal salario, StatusCliente status, Cartao cartao) {

        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = status;
        this.cartao = cartao;
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

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusCliente getStatus() {
        return status;
    }

    public String getCartao() {

        return cartao != null  ? cartao.getId() : new Cartao("naoEmitido").getId();
    }
}
