package br.com.zup.Treinopropostas.Cartao;

public class CarteiraFeignResult {

    private String resultado;

    private String id;

    @Deprecated
    public CarteiraFeignResult(){}

    public CarteiraFeignResult(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
