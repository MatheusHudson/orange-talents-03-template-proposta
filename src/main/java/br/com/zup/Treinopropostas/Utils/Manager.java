package br.com.zup.Treinopropostas.Utils;


import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.function.Supplier;


@Component
public class Manager {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public <T> T salvaEComita(T objeto) {
        manager.persist(objeto);
        return objeto;
    }

    @Transactional
    public <T> T atualizaEComita(T objeto) {
        return manager.merge(objeto);
    }

    @Transactional
    public <T> T executa(Supplier<T> algumCodigoComRetorno){
        return algumCodigoComRetorno.get();
    }

    public Boolean noExistValue(String entity,  String value, String field) {

        return  !manager.createQuery("Select count(a) > 0 From " + entity + " a WHERE a."+ field +" = :value", Boolean.class).setParameter("value", value).getSingleResult();
    }
}