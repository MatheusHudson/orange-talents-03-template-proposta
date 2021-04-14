package br.com.zup.Treinopropostas.Proposta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Locale;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpfOrCnpj;
    private String email;
    private String nome;
    private String endereco;
    private Double salario;

    public Proposta(String cpfOrCnpj, String email, String nome, String endereco, Double salario) {
        this.cpfOrCnpj = cpfOrCnpj;
        this.email = email.toLowerCase(Locale.ROOT);
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }
}
