package br.com.zup.Treinopropostas.Proposta;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface  PropostaRepository extends CrudRepository<Proposta, Long> {

    @Query("Select count(a) > 0 From Proposta a WHERE :campo = :value")
    boolean existValue(String value,String campo);

    @Query(nativeQuery = true,value = "Select * From Proposta p WHERE p.status =  'SEM_RESTRICAO'  AND p.cartao_Id = 'naoEmitido' ORDER BY p.id ASC LIMIT 1 ")
    Optional<Proposta> getPropostaSemCartaoESemRestricao();

    Optional<Proposta> findByDocumento(String documento);
}
