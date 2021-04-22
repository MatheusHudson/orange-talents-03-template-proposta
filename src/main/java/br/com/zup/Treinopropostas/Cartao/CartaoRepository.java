package br.com.zup.Treinopropostas.Cartao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartaoRepository extends CrudRepository<Cartao, String> {

    Optional<Cartao> findById(String cartao);
}
