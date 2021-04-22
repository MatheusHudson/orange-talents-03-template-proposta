package br.com.zup.Treinopropostas.Cartao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    private String id;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    Set<Biometria> biometrias= new HashSet<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }


    public void adicionaBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }
}
