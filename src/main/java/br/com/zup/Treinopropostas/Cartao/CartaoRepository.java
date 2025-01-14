package br.com.zup.Treinopropostas.Cartao;


import br.com.zup.Treinopropostas.Cartao.Enum.SolicitacaoStatusBloqueio;
import br.com.zup.Treinopropostas.Carteira.Enum.CarteirasTipo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartaoRepository extends CrudRepository<Cartao, String> {

    Optional<Cartao> findById(String cartao);
    boolean existsById(String cartao);
    Boolean existsByBloqueioStatusAndBloqueioCartao(SolicitacaoStatusBloqueio statusBloqueio, Cartao cartao);

    Boolean existsByCarteiraSetCartaoAndCarteiraTipoCarteira(Cartao cartao, CarteirasTipo carteiras);

}
