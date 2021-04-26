package br.com.zup.Treinopropostas.Validations;

import br.com.zup.Treinopropostas.Utils.ApiErrorException;
import br.com.zup.Treinopropostas.Utils.Resultado;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;


public class ErroPadronizado {

    private Collection<String> mensagens;

    public ErroPadronizado(Collection<String> mensagens) {
        this.mensagens = mensagens;
    }

    public Collection<String> getMensagens() {
        return mensagens;
    }



}
