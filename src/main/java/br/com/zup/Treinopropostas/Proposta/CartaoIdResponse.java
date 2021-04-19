package br.com.zup.Treinopropostas.Proposta;

public class CartaoIdResponse {

    private String id;

    @Deprecated
    public CartaoIdResponse() {

    }

    public String getId() {
        return id;
    }
    public CartaoIdResponse(String id) {
        this.id = id;
    }

}
