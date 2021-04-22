package br.com.zup.Treinopropostas.Cartao;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    private String fingerPrint;

    @CreationTimestamp
    private LocalDateTime instateCriacao = LocalDateTime.now();

    @Deprecated
    public Biometria() {

    }

    public Biometria(Cartao cartao, String fingerPrint) {
        this.cartao = cartao;
        this.fingerPrint = fingerPrint;
    }

    public Long getId() {
        return id;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public BiometriaResponse toResponse() {
        return new BiometriaResponse(fingerPrint, cartao.getId(), instateCriacao);
    }
}
