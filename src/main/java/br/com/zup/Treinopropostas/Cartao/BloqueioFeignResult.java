package br.com.zup.Treinopropostas.Cartao;

import org.springframework.stereotype.Component;


public class BloqueioFeignResult {

    private String resultado;

    @Deprecated
    public BloqueioFeignResult() {}

    public BloqueioFeignResult(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}


