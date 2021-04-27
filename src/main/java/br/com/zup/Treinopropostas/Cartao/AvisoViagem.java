package br.com.zup.Treinopropostas.Cartao;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinoViagem;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTerminoViagem;

    @CreationTimestamp
    private LocalDateTime instanteCriacao = LocalDateTime.now();

    @NotBlank
    private String ipRequisicao;

    @NotBlank
    private String userAgentRequisicao;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public  AvisoViagem() {

    }

    public AvisoViagem(String destinoViagem, LocalDate dataTerminoViagem, String ipRequisicao, String userAgentRequisicao, Cartao cartao) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.ipRequisicao = ipRequisicao;
        this.userAgentRequisicao = userAgentRequisicao;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public AvisoViagemResponse toResponse(String status) {
        return new AvisoViagemResponse(destinoViagem,dataTerminoViagem,instanteCriacao,ipRequisicao,userAgentRequisicao,cartao, status );
    }
}
