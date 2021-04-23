package br.com.zup.Treinopropostas.Cartao;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

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

    public Boolean isBase64() {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] decode = Base64.getDecoder().decode(fingerPrint.getBytes());
            String mensagemDecodificada = new String(decode);
            return true;
        } catch(IllegalArgumentException e) {
            return  false;
        }
    }


}


