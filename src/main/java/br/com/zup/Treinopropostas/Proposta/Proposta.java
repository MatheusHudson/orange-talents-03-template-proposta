package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;

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

    private String cartaoId;

    @Deprecated
    public  Proposta() {}


    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario, String cartaoId) {
        this.documento = documento;
        this.email = email.toLowerCase(Locale.ROOT);
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.cartaoId = cartaoId;
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

    public String getCartaoId() {
        return cartaoId;
    }

    public Solicitacao enviarInformacoes() {
        return new Solicitacao(documento, nome, id.toString());
    }

    public void atualizaEntidade(Solicitacao solicitacao) {
        status = solicitacao.getResultadoSolicitacao();
    }

    public void atualizaEntidade(CartaoIdResponse cartaoId) {
        this.cartaoId = cartaoId.getId();
    }

    public PropostaResponse toResponse() {
        return new PropostaResponse(documento, email, nome, endereco, salario, status);
    }
}
