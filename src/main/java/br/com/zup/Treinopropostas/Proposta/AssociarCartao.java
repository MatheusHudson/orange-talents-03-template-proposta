package br.com.zup.Treinopropostas.Proposta;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;

@Component
public class AssociarCartao {

    private final PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(AssociarCartao.class);

    private CartaoResource cartaoResource;

    public AssociarCartao(PropostaRepository propostaRepository, CartaoResource cartaoResource) {
        this.propostaRepository = propostaRepository;
        this.cartaoResource = cartaoResource;
    }

    @Scheduled(fixedDelay = 5000)
    public void associarCartao() {
        Optional<Proposta> possivelProposta = propostaRepository.getPropostaSemCartaoESemRestricao();
        if (possivelProposta.isPresent()) {
            Proposta prosposta = possivelProposta.get();
            try {
                CartaoIdResponse cartaoId = cartaoResource.getCartao(Map.of("idProposta", prosposta.getId()));
                prosposta.atualizaEntidade(cartaoId);
                propostaRepository.save(prosposta);
                logger.info("A proposta com id={} foi associado com o cartão de id={}", prosposta.getId(), cartaoId.getId());
            } catch (FeignException.FeignClientException e) {
                logger.info("Não foi possivel associar a proposta com id={} a um cartão.", prosposta.getId());
            }
        } else {
            logger.info("Não há propostas em condições de ser vinculada há um novo cartão.");
        }


    }
}