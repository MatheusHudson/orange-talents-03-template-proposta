package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Cartao.Cartao;
import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.crypto.KeyGenerator;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Locale;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String documento;
    @Email
    @NotBlank

    @Column(nullable = false)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotBlank
    @Column(nullable = false)
    private String endereco;
    @NotNull
    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusCliente status;

    @OneToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;

    @Deprecated
    public  Proposta() {}


    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {

        this.documento = documento;
              //  Encryptors.text(documento.replaceAll("[^0-9]", ""), KeyGenerators.string().generateKey()).decrypt(encrypt);
        this.email = email.toLowerCase(Locale.ROOT);
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = StatusCliente.NAO_VERIFICADO;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public StatusCliente getStatus() {
        return status;
    }

    public String getNome() {
        return nome;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Solicitacao enviarInformacoes() {
        return new Solicitacao(documento, nome, id.toString());
    }

    public void atualizaEntidade(Solicitacao solicitacao) {
        status =  solicitacao.getResultadoSolicitacao();
    }

    public void atualizaEntidade(Cartao cartao) {
        this.cartao = cartao;
    }

    public PropostaResponse toResponse() {
        return new PropostaResponse(email, nome, endereco, salario, status, cartao);
    }


}
