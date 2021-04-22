package br.com.zup.Treinopropostas.Cartao;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private String fingerPrint;

    public String getFingerPrint() {
        return fingerPrint;
    }

    @Deprecated
    public BiometriaRequest(){
    }

    public BiometriaRequest(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(cartao, fingerPrint);
    }
}


