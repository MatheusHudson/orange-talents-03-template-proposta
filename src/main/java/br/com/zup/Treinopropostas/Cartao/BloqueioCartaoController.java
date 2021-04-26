package br.com.zup.Treinopropostas.Cartao;

import br.com.zup.Treinopropostas.Cartao.Enum.SolicitacaoStatusBloqueio;
import br.com.zup.Treinopropostas.Feign.CartaoResource;
import br.com.zup.Treinopropostas.Utils.ApiErrorException;
import br.com.zup.Treinopropostas.Utils.Resultado;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;


@RestController
@Validated
public class BloqueioCartaoController {

    private final CartaoRepository cartaoRepository;
    private final CartaoResource cartaoResource;
    private final Logger logger = LoggerFactory.getLogger(BloqueioCartaoController.class);


    public BloqueioCartaoController(CartaoRepository cartaoRepository, CartaoResource cartaoResource) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoResource = cartaoResource;

    }


    @PostMapping("/cartao/{id}/bloqueio")
    public ResponseEntity<?> bloquearCartao(@PathVariable @NotBlank @Valid String id,
                                            @RequestHeader HttpHeaders headers, UriComponentsBuilder uriBuilder) {

        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isPresent()) {
            if (!cartaoRepository.existsByBloqueioStatus(SolicitacaoStatusBloqueio.BLOQUEADO)) {
                Cartao cartao = possivelCartao.get();
                BloqueioFeignResult bloqueioFeignResult;
                try {
                    bloqueioFeignResult = cartaoResource.bloquearCartao(id, Map.of("sistemaResponsavel", "Propostas"));
                } catch (FeignException.FeignClientException e) {
                    bloqueioFeignResult = new BloqueioFeignResult("FALHA");
                }
                    SolicitacaoStatusBloqueio solicitacaoStatusBloqueio = SolicitacaoStatusBloqueio.valueOf(bloqueioFeignResult.getResultado());
                    Bloqueio bloqueio = new Bloqueio(headers.getHost().getHostName(),
                            headers.get("User-Agent").get(0), solicitacaoStatusBloqueio, cartao);
                    cartao.atualizaCartao(bloqueio);
                    cartaoRepository.save(cartao);
                    BloqueioResponse response = bloqueio.toResponse();
                    URI uri = uriBuilder.path("/cartao/" + id + "bloqueio/{id}").buildAndExpand(bloqueio.getId()).toUri();
                    logger.info("Cartao com o id = {} resultou no status = {} ", cartao.getId(), bloqueio.getStatus());

                    return ResponseEntity.created(uri).body(Resultado.sucesso(response).getSucesso());

                } else{
                    Collection<String> mensagens = new ArrayList<>();
                    mensagens.add(Resultado.erro(new ApiErrorException("Não é possivel solicitar bloqueio para um cartão já bloqueado")).getExcecao().getMessage());

                    ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
                    logger.info("Quebra de regra de negócio pois o cartão com o id: ={} já está bloqueado", id);

                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(erroPadronizado);
                }
            } else {
                Collection<String> mensagens = new ArrayList<>();
                mensagens.add(Resultado.erro(new ApiErrorException("Não foi encontrado um cartão com este id!")).getExcecao().getMessage());

                ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
                logger.info("Nao foi encontrado o id: ={}.", id);

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(erroPadronizado);
            }
        }
    }