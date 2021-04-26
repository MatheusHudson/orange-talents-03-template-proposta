package br.com.zup.Treinopropostas.Cartao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    private String id;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Biometria> biometrias= new HashSet<>();

    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private Bloqueio bloqueio;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id) {

        this.id = id;
    }


    public String getId() {
        return id;
    }

    public Bloqueio getBloqueio() {
        return bloqueio;
    }

    public void adicionaBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }

    public void atualizaCartao(Bloqueio bloqueio) {
        this.bloqueio = bloqueio;
    }
}
