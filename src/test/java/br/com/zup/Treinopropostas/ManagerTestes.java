package br.com.zup.Treinopropostas;

import br.com.zup.Treinopropostas.Proposta.Proposta;
import br.com.zup.Treinopropostas.Utils.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;


@SpringBootTest
@AutoConfigureDataJpa
public class ManagerTestes {

    @PersistenceContext
    EntityManager em;

    @Autowired
    Manager manager;

    @Test
    @Transactional
    @DisplayName("noExistValueTest")
    void test1() {
        Proposta proposta = new Proposta("686.465.700-02" ,
                "matehus@teste.com", "Matheus", "Rua A", new BigDecimal(54987.47));

        Proposta proposta2 = new Proposta("686.465.700-00" ,
                "matehus@teste.com", "Matheus", "Rua A", new BigDecimal(54987.47));
        em.persist(proposta);
        System.out.println(proposta);

        Assertions.assertEquals(false, manager.noExistValue("Proposta", "686.465.700-02", "documento"));
        Assertions.assertEquals(true, manager.noExistValue("Proposta", "686.465.700-00", "documento"));
        Assertions.assertEquals(true, manager.noExistValue("Proposta", "", "documento"));
    }

}
