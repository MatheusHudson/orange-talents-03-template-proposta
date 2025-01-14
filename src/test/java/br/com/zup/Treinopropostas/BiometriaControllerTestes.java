package br.com.zup.Treinopropostas;

import br.com.zup.Treinopropostas.Cartao.BiometriaRequest;
import br.com.zup.Treinopropostas.Proposta.*;
import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import br.com.zup.Treinopropostas.Feign.CartaoResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class BiometriaControllerTestes {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    CartaoResource cartaoResource;

    @Autowired
    private ObjectMapper jsonMapper;


    @Test
    @DisplayName("deveriaCriarUmaBiometria")
    @Transactional
    public void test1() throws Exception {
        Proposta proposta = new Proposta("686.465.700-02", "matheus@teste.com", "matheus", "Rua A", new BigDecimal(7015.44));
        proposta.atualizaEntidade(new Solicitacao(StatusCliente.SEM_RESTRICAO));
        propostaRepository.save(proposta);
        AssociarCartaoAProposta associarCartao = new AssociarCartaoAProposta(propostaRepository, cartaoResource);
        associarCartao.associarCartao();
        BiometriaRequest request = new BiometriaRequest("dGVzdCBpbnB1dA==");
        mockMvc.perform(post("/cartao/" + proposta.getCartao().getId() + "/biometria")
                .content(json(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @DisplayName("naoDeveriaCriarUmaBiometria")
    @Transactional
    public void test2() throws Exception {
        Proposta proposta = new Proposta("686.465.700-02", "matheus@teste.com", "matheus", "Rua A", new BigDecimal(7015.44));
        proposta.atualizaEntidade(new Solicitacao(StatusCliente.SEM_RESTRICAO));
        propostaRepository.save(proposta);
        AssociarCartaoAProposta associarCartao = new AssociarCartaoAProposta(propostaRepository, cartaoResource);
        associarCartao.associarCartao();
        BiometriaRequest request = new BiometriaRequest("fdsaw  fsafas  safsaf w");
      MvcResult mvcResult = mockMvc.perform(post("/cartao/" + proposta.getCartao().getId() + "/biometria")
              .content(json(request))
              .contentType(MediaType.APPLICATION_JSON))
              .andReturn();
      Integer status = mvcResult.getResponse().getStatus();
      Assertions.assertTrue(status.equals(400));
  }




    public String json(BiometriaRequest request) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(request);
    }


}
