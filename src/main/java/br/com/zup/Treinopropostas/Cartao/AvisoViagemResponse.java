package br.com.zup.Treinopropostas.Cartao;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AvisoViagemResponse {

    private String destinoViagem;


    private LocalDate dataTerminoViagem;

    @CreationTimestamp
    private LocalDateTime instanteCriacao = LocalDateTime.now();

    @NotBlank
    private String ipRequisicao;

    @NotBlank
    private String userAgentRequisicao;

    @ManyToOne
    private String cartao;

    public AvisoViagemResponse(String destinoViagem, LocalDate dataTerminoViagem, LocalDateTime instanteCriacao, String ipRequisicao, String userAgentRequisicao, Cartao cartao) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.instanteCriacao = instanteCriacao;
        this.ipRequisicao = ipRequisicao;
        this.userAgentRequisicao = userAgentRequisicao;
        this.cartao = cartao.getId();
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }

    public String getIpRequisicao() {
        return ipRequisicao;
    }

    public String getUserAgentRequisicao() {
        return userAgentRequisicao;
    }

    public String getCartao() {
        return cartao;
    }
}
