package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import br.com.zup.Treinopropostas.Validations.Utils.ApiErrorException;
import br.com.zup.Treinopropostas.Validations.Utils.Resultado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class PropostaController {

    @PersistenceContext
    EntityManager em;

    @PostMapping("/proposta")
    @Transactional
    public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {

        if(request.isValid(em)){
            Proposta proposta = request.toModel();
            em.persist(proposta);
            URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
            return  ResponseEntity.created(uri).body(Resultado.sucesso(request).getSucesso());
        } else {
            Collection<String> mensagens = new ArrayList<>();
            mensagens.add(Resultado.erro(new ApiErrorException("Documento já existente")).getExcecao().getMessage());

            ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(erroPadronizado);
        }

    }

}
