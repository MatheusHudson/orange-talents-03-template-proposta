package br.com.zup.Treinopropostas.Proposta;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface  PropostaRepository extends CrudRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    @Query(nativeQuery = true,value = "Select * From proposta p WHERE p.status =  'SEM_RESTRICAO'  AND p.cartao_Id is null ORDER BY p.id ASC LIMIT 1 ")
    Optional<Proposta> getPropostaSemCartaoESemRestricao();

    Optional<Proposta> findByDocumento(String documento);
}
