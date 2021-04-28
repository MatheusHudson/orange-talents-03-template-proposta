package br.com.zup.Treinopropostas.Carteira;

import br.com.zup.Treinopropostas.Cartao.Cartao;
import br.com.zup.Treinopropostas.Carteira.Enum.CarteirasTipo;
import br.com.zup.Treinopropostas.Carteira.Enum.StatusCartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Carteira {

    @Id
    @NotBlank
    private String id;

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @Enumerated(EnumType.STRING)
    private CarteirasTipo tipoCarteira;

    @Deprecated
    public Carteira() {}


    public Carteira(String id, String email, StatusCartao statusCartao, CarteirasTipo carteira) {
        this.id = id;
        this.email = email;
        this.statusCartao = statusCartao;
        this.tipoCarteira = carteira;
    }

    @OneToMany
    private Set<Cartao> setCartao = new HashSet<>();


    public String getId() {
        return id;
    }

    public void adicionaCartao(Cartao cartao) {
        setCartao.add(cartao);
    }

    public CarteiraResponse toRespone() {
        return  new CarteiraResponse(id, email, statusCartao);
    }
}
