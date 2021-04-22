package br.com.zup.Treinopropostas.Cartao;

import java.time.LocalDateTime;

public class BiometriaResponse {

    private String fingerPrint;
    private String cartaoId;
    private LocalDateTime instanteCriacao;

    public BiometriaResponse(String fingerPrint, String cartaoId, LocalDateTime instanteCriacao) {
        this.fingerPrint = fingerPrint;
        this.cartaoId = cartaoId;
        this.instanteCriacao = instanteCriacao;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public String getCartaoId() {
        return cartaoId;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }
}
