package br.com.zup.Treinopropostas.Carteira;



import br.com.zup.Treinopropostas.Carteira.Enum.StatusCartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String carteira;

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }

    @Override
    public String toString() {
        return "CarteiraRequest{" +
                "email='" + email + '\'' +
                ", carteira='" + carteira + '\'' +
                '}';
    }
}
