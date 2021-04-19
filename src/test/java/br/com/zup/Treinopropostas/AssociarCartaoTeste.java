package br.com.zup.Treinopropostas;

import br.com.zup.Treinopropostas.Proposta.*;
import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
public class AssociarCartaoTeste {


    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private CartaoResource cartaoResource;

    @Test
    @DisplayName("deveriaRetornarTrue")
    @Transactional
    public void test1()  {
        Proposta proposta = new Proposta("686.465.700-02", "matheus@teste.com", "matheus", "Rua A", new BigDecimal(7015.44), "naoEmitido");
        proposta.atualizaEntidade(new Solicitacao(StatusCliente.SEM_RESTRICAO));
        propostaRepository.save(proposta);
        Optional<Proposta> propostaSemCartaoESemRestricao = propostaRepository.getPropostaSemCartaoESemRestricao();
        Assertions.assertTrue(propostaSemCartaoESemRestricao.isPresent());
    }
}
