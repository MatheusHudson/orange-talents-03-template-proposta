package br.com.zup.Treinopropostas;

import br.com.zup.Treinopropostas.Proposta.*;
import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class ListarPropostaControllerTestes {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private ObjectMapper jsonMapper;


    @Test
    @DisplayName("deveriaRetornarUmPropostaResponse")
    @Transactional
    public void test1() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("686.465.700-02", "matheus@teste.com", "matheus", "Rua A", new BigDecimal(7015.44));
        Proposta proposta = propostaRequest.toModel();
        proposta.atualizaEntidade(new Solicitacao(StatusCliente.SEM_RESTRICAO));
        propostaRepository.save(proposta);
        PropostaResponse response = proposta.toResponse();
        mockMvc.perform(get("/proposta/686.465.700-02")
                .content(json(propostaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertTrue(response.getDocumento() != null);
        Assertions.assertTrue(response.getStatus() != null);
        Assertions.assertTrue(response.getEmail() != null);
        Assertions.assertTrue(response.getEndereco() != null);
        Assertions.assertTrue(response.getSalario() != null);
        Assertions.assertTrue(response.getNome() != null);



    }


    public String json(PropostaRequest request) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(request);
    }
  public String json(PropostaResponse response) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(response);
    }


}
