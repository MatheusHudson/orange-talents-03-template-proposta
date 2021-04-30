package br.com.zup.Treinopropostas.Carteira;

import br.com.zup.Treinopropostas.Cartao.Cartao;
import br.com.zup.Treinopropostas.Cartao.CartaoRepository;
import br.com.zup.Treinopropostas.Carteira.Enum.CarteirasTipo;
import br.com.zup.Treinopropostas.Carteira.Enum.StatusCartao;
import br.com.zup.Treinopropostas.Feign.CartaoResource;
import br.com.zup.Treinopropostas.Cartao.CarteiraFeignResult;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
public class AsssociarCartaoACarteiraController {

    private  final CartaoRepository cartaoRepository;
    private final CartaoResource cartaoResource;
    private final CarteiraRequestValidator carteiraRequestValidator;
    private final Metricas metricas;
    private final Logger logger = LoggerFactory.getLogger(AsssociarCartaoACarteiraController.class);


    public AsssociarCartaoACarteiraController(CartaoRepository cartaoRepository, CartaoResource cartaoResource, CarteiraRequestValidator carteiraRequestValidator, Metricas metricas) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoResource = cartaoResource;
        this.carteiraRequestValidator = carteiraRequestValidator;
        this.metricas = metricas;
    }

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(carteiraRequestValidator);
    }

    @PostMapping("cartao/{id}/carteira")
    public ResponseEntity<?> associarCarteiraPaypal(@RequestBody @Valid CarteiraRequest request, @PathVariable String id, UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            if(!cartaoRepository.existsByCarteiraSetCartaoAndCarteiraTipoCarteira(cartao, CarteirasTipo.valueOf(request.getCarteira()))) {
                try {
                    CarteiraFeignResult carteiraFeignResult = cartaoResource.associarCarteira(id, Map.of("email", request.getEmail(), "carteira", CarteirasTipo.valueOf(request.getCarteira()).toString()));
                    Carteira carteiraPaypal = new Carteira(carteiraFeignResult.getId(),
                            request.getEmail(), StatusCartao.valueOf(carteiraFeignResult.getResultado()), CarteirasTipo.valueOf(request.getCarteira()));
                    carteiraPaypal.adicionaCartao(cartao);
                    cartao.atualizaCartao(carteiraPaypal);

                    cartaoRepository.save(cartao);
                    CarteiraResponse response = carteiraPaypal.toRespone();
                    metricas.meuContador();
                    logger.info("Solicitação de associar o cartao com id= {} a carteira com id= {} resultou em {}. ", cartao.getId(), carteiraPaypal.getId(),response.getStatusCartao().toString());

                    URI uri = uriComponentsBuilder.path("cartao/{id}/carteira/{idCarteira}").buildAndExpand(cartao.getId(), carteiraPaypal.getId()).toUri();
                    return ResponseEntity.created(uri).body(Resultado.sucesso(response).getSucesso());
                }catch (FeignException.FeignClientException e) {
                    ErroPadronizado erroPadronizado = new ErroPadronizado(Resultado.erro(new ApiErrorException(
                            "Não foi possivel realizar sua solicitação de associar o cartão devido quebra de regra de negocio.")).getExcecao().getMessage());
                    logger.info("O feign retornou uma exception para o id = {} e  requisição {}  ", id, request);

                    return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(erroPadronizado);
                }

            } else {
                ErroPadronizado erroPadronizado = new ErroPadronizado(Resultado.erro(new ApiErrorException("Este cartão já foi associado a esta carteira")).getExcecao().getMessage());
                logger.info("Erro 422 ao realizar validação de regra de negocio, cartão com id= {} já associado a carteira.", cartao.getId());

                return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(erroPadronizado);
            }
        } else {
            ErroPadronizado erroPadronizado = new ErroPadronizado(Resultado.erro(new ApiErrorException("Não foi possivel encontrar um cartão com este id")).getExcecao().getMessage());
            logger.info("O id = {} não foi encontrado no banco de dados", id);

            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(erroPadronizado);
        }
    }
}
