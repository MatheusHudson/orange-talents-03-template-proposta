package br.com.zup.Treinopropostas;

import br.com.zup.Treinopropostas.Proposta.PropostaRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.stream.Stream;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class CriarPropostaControllerTestes {

    @Autowired
    MockMvc mockMvc;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ObjectMapper jsonMapper;


    @DisplayName("naoDeveriaCriarProposta")
    @ParameterizedTest
    @MethodSource("providePropostaRequest")
    @Transactional
    public void test1(PropostaRequest propostaRequest, Integer codigoStatus) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/proposta")
                .content(json(propostaRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Integer status = mvcResult.getResponse().getStatus();

        assertEquals(true,status.equals(codigoStatus));
    }


    @Test
    @DisplayName("deveriaCriarUmaProposta")
    @Transactional
    public void test2() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("686.465.700-02", "matheus@teste.com", "matheus", "Rua A", new BigDecimal(7015.44));
        MvcResult mvcResult = mockMvc.perform(post("/proposta")
                .content(json(propostaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }


    private static Stream<Arguments> providePropostaRequest() {
        PropostaRequest propostaRequest = new PropostaRequest("686.465.700-02", "matheus@teste.com", "matheus", "Rua A",  new BigDecimal(7015.44));
        PropostaRequest semEmail = new PropostaRequest("686.465.700-02", "", "matheus", "Rua A",  new BigDecimal(7015.44));
        PropostaRequest semDocumento = new PropostaRequest("", "matheus@teste.com", "matheus", "Rua A",  new BigDecimal(7015.44));
        PropostaRequest documentoInvalido = new PropostaRequest("165487414", "matheus@teste.com", "matheus", "Rua A",  new BigDecimal(7015.44));
        PropostaRequest semNome =  new PropostaRequest("686.465.700-02", "matheus@teste.com", "", "Rua A",  new BigDecimal(7015.44));
        return Stream.of(
                Arguments.of(semEmail, 400),
                Arguments.of(semDocumento, 400),
                Arguments.of(documentoInvalido, 400),
                Arguments.of(semNome, 400)
        );
    }

    public String json(PropostaRequest request) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(request);
    }


}
