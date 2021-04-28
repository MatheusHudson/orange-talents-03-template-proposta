package br.com.zup.Treinopropostas.Carteira;

import br.com.zup.Treinopropostas.Cartao.Cartao;
import br.com.zup.Treinopropostas.Cartao.CartaoRepository;
import br.com.zup.Treinopropostas.Carteira.Enum.StatusCartao;
import br.com.zup.Treinopropostas.Feign.CartaoResource;
import br.com.zup.Treinopropostas.Feign.CarteiraFeignResult;
import br.com.zup.Treinopropostas.Meters.Metricas;
import br.com.zup.Treinopropostas.Utils.ApiErrorException;
import br.com.zup.Treinopropostas.Utils.Resultado;
import br.com.zup.Treinopropostas.Validations.CarteiraRequestValidator;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
public class AsssociarCartaoACarteiraController {

    private  final CartaoRepository cartaoRepository;
    private final CartaoResource cartaoResource;
    private final Metricas metricas;
    private final CarteiraRequestValidator carteiraRequestValidator;
    private final Logger logger = LoggerFactory.getLogger(AsssociarCartaoACarteiraController.class);


    public AsssociarCartaoACarteiraController(CartaoRepository cartaoRepository, CartaoResource cartaoResource, Metricas metricas, CarteiraRequestValidator carteiraRequestValidator) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoResource = cartaoResource;
        this.metricas = metricas;
        this.carteiraRequestValidator = carteiraRequestValidator;
    }

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(carteiraRequestValidator);
    }


    @PostMapping("cartao/{id}/carteira")
    public ResponseEntity<?> associarCarteira(@RequestBody @Valid CarteiraRequest request, @PathVariable String id, UriComponentsBuilder uriComponentsBuilder) {


        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            if(!cartaoRepository.existsByCarteiraPaypalSetCartao(cartao)) {
                CarteiraFeignResult carteiraFeignResult;
                try {
                     carteiraFeignResult = cartaoResource.associarCarteira(id, Map.of("email", request.getEmail(), "carteira", request.getCarteira()));
                    CarteiraPaypal carteiraPaypal = new CarteiraPaypal(carteiraFeignResult.getId(),
                            request.getEmail(), StatusCartao.valueOf(carteiraFeignResult.getResultado()));
                    carteiraPaypal.adicionaCartao(cartao);
                    cartao.atualizaCartao(carteiraPaypal);

                    cartaoRepository.save(cartao);
                    CarteiraPaypalResponse response = carteiraPaypal.toRespone();
                    metricas.meuContador();
                    logger.info("Solicitação de associar o cartao com id= {} a carteira com id= {} resultou em {}. ", cartao.getId(), carteiraPaypal.getId(),response.getStatusCartao().toString());

                    URI uri = uriComponentsBuilder.path("cartao/{id}/carteira/{idCarteira}").buildAndExpand(cartao.getId(), carteiraPaypal.getId()).toUri();
                    return ResponseEntity.created(uri).body(Resultado.sucesso(response).getSucesso());
                }catch (FeignException.FeignClientException e) {
                    Collection<String> mensagens = new ArrayList<>();
                    mensagens.add(Resultado.erro(new ApiErrorException(
                            "Não foi possivel realizar sua solicitação de associar o cartão devido quebra de regra de negocio.")).getExcecao().getMessage());
                    ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
                    logger.info("O feign retornou uma exception para o id = {} e  requisição {}  ", id, request);

                    return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(erroPadronizado);
                }

            } else {
                Collection<String> mensagens = new ArrayList<>();
                mensagens.add(Resultado.erro(new ApiErrorException("Este cartão já foi associado a esta carteira")).getExcecao().getMessage());

                ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
                logger.info("Erro 422 ao realizar validação de regra de negocio, cartão com id= {} já associado a carteira.", cartao.getId());

                return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(erroPadronizado);
            }

        } else {
            Collection<String> mensagens = new ArrayList<>();
            mensagens.add(Resultado.erro(new ApiErrorException("Não foi possivel encontrar um cartão com este id")).getExcecao().getMessage());

            ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
            logger.info("O id = {} não foi encontrado no banco de dados", id);

            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(erroPadronizado);
        }
    }

}
