package br.com.zup.Treinopropostas.Carteira;

import br.com.zup.Treinopropostas.Carteira.Enum.StatusCartao;

public class CarteiraResponse {

    private String id;

    private String email;

    private StatusCartao statusCartao;

    public CarteiraResponse(String id, String email, StatusCartao statusCartao) {
        this.id = id;
        this.email = email;
        this.statusCartao = statusCartao;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public StatusCartao getStatusCartao() {
        return statusCartao;
    }
}
