package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import br.com.zup.Treinopropostas.Validations.CPForCNPJ;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;


public class PropostaRequest {

    @CPForCNPJ
    @NotBlank
    @JsonFormat
    private String documento;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    private StatusCliente status;



    @Positive
    @NotNull
    private BigDecimal salario;

    public PropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
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

    public Proposta toModel(String salt) {
        this.documento = documento.replaceAll("[^0-9]", "");
        this.documento = Encryptors.text(documento,salt).encrypt(documento);
        return new Proposta(documento, email, nome, endereco, salario);
    }

}
