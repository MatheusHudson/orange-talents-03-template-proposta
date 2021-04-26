package br.com.zup.Treinopropostas.Cartao;

import br.com.zup.Treinopropostas.Cartao.Enum.SolicitacaoStatusBloqueio;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime instanteCriacao = LocalDateTime.now();

    @NotBlank
    private String host;

    @NotBlank
    private String userAgent;

    @OneToOne
    Cartao cartao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SolicitacaoStatusBloqueio status;

    @Deprecated
    public Bloqueio() {

    }

    public Bloqueio(String host, String userAgent,SolicitacaoStatusBloqueio status, Cartao cartao) {

        this.host = host;
        this.userAgent = userAgent;
        this.cartao = cartao;
        this.status = status;
    }

    public SolicitacaoStatusBloqueio getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public BloqueioResponse toResponse() {

        return  new BloqueioResponse(instanteCriacao, host,userAgent ,status);
    }
}
