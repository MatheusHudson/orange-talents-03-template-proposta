package br.com.zup.Treinopropostas.Cartao;

import br.com.zup.Treinopropostas.Meters.Metricas;
import br.com.zup.Treinopropostas.Utils.ApiErrorException;
import br.com.zup.Treinopropostas.Utils.Resultado;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    private final CartaoRepository cartaoRepository;
    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);
    private final Metricas metricas;


    public AvisoViagemController(CartaoRepository cartaoRepository, Metricas metricas) {
        this.cartaoRepository = cartaoRepository;
        this.metricas = metricas;
    }

    @PostMapping("/cartao/{id}/aviso-viagem")
        public ResponseEntity<?> criarAvisoDeViagem(@RequestBody @Valid AvisoViagemRequest request, @PathVariable String id,
                                                    UriComponentsBuilder uriComponentsBuilder, @RequestHeader HttpHeaders headers){

        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            AvisoViagem avisoViagem = new AvisoViagem(request.getDestinoViagem(),
                    request.getDataTerminoViagem(), headers.getHost().getHostName(), headers.get("User-Agent").get(0), cartao);
            cartao.atualizaCartao(avisoViagem);
            cartaoRepository.save(cartao);
            metricas.meuContador();
            AvisoViagemResponse response = avisoViagem.toResponse();

            URI uri = uriComponentsBuilder.path("/cartao/{idCartao}/aviso-viagem/{id}")
                    .buildAndExpand(cartao.getId(),avisoViagem.getId()).toUri();

            logger.info("Aviso de viagem gerado para o cartão com o id= {}", id);
            return ResponseEntity.created(uri).body(Resultado.sucesso(response).getSucesso());
        } else {
            Collection<String> mensagens = new ArrayList<>();
            mensagens.add(Resultado.erro(new ApiErrorException("Id não encontrado")).getExcecao().getMessage());

            ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
            logger.info("O id= {} nao foi encontrado", id);

            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(erroPadronizado);
        }
    }
}
