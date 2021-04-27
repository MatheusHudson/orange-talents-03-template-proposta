package br.com.zup.Treinopropostas.Cartao;

public class AvisoFeignResult {

    private String resultado;

    @Deprecated
    AvisoFeignResult(){}


    public AvisoFeignResult(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }


}
