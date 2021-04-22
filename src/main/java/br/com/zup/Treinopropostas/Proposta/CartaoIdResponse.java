package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Cartao.Cartao;

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

    public Cartao toModel() {
        return new Cartao(id);
    }
}
