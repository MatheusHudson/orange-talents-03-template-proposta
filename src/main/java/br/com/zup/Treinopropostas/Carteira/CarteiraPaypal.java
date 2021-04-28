package br.com.zup.Treinopropostas.Carteira;

import br.com.zup.Treinopropostas.Cartao.Cartao;
import br.com.zup.Treinopropostas.Carteira.Enum.StatusCartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CarteiraPaypal {

    @Id
    @NotBlank
    private String id;

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @Deprecated
    public CarteiraPaypal() {}


    public CarteiraPaypal(String id, String email, StatusCartao statusCartao) {
        this.id = id;
        this.email = email;
        this.statusCartao = statusCartao;
    }

    @OneToMany
    private Set<Cartao> setCartao = new HashSet<>();


    public String getId() {
        return id;
    }

    public void adicionaCartao(Cartao cartao) {
        setCartao.add(cartao);
    }

    public CarteiraPaypalResponse toRespone() {
        return  new CarteiraPaypalResponse(id, email, statusCartao);
    }
}
