package br.com.zup.Treinopropostas.Cartao;

import br.com.zup.Treinopropostas.Cartao.Enum.SolicitacaoStatusBloqueio;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BloqueioResponse {

    @NotNull
    private LocalDateTime instanteCriacao;

    @NotBlank
    private String host;

    @NotBlank
    private String userAgent;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SolicitacaoStatusBloqueio status;

    public BloqueioResponse(LocalDateTime instanteCriacao, String host, String userAgent, SolicitacaoStatusBloqueio status) {
        this.instanteCriacao = instanteCriacao;
        this.host = host;
        this.userAgent = userAgent;
        this.status = status;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }

    public String getHost() {
        return host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public SolicitacaoStatusBloqueio getStatus() {
        return status;
    }
}
