package br.com.zup.Treinopropostas.Cartao;


import br.com.zup.Treinopropostas.Utils.ApiErrorException;
import br.com.zup.Treinopropostas.Utils.Resultado;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.Base64;
import java.util.Optional;

@RestController
public class BiometriaController {

    private final CartaoRepository cartaoRepository;
    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/cartao/{id}/biometria")
    public ResponseEntity<?> cadastrarBiometria(@PathVariable String id, @RequestBody  @Valid BiometriaRequest request, UriComponentsBuilder uriBuilder) {
        if(request.isBase64()) {
            Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
            if (possivelCartao.isPresent()) {
                Cartao cartao = possivelCartao.get();
                Biometria biometria = request.toModel(cartao);
                cartao.adicionaBiometria(biometria);
                cartaoRepository.save(cartao);
                BiometriaResponse biometriaResponse = biometria.toResponse();
                URI uri = uriBuilder.path("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
                return ResponseEntity.created(uri).body(Resultado.sucesso(biometriaResponse).getSucesso());
            } else {
                logger.info("Cartao com o id " + id + "nao existe");
                ErroPadronizado erroPadronizado = new ErroPadronizado(Resultado.erro(new ApiErrorException("Nao existe um cartao com este id!")).getExcecao().getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body(erroPadronizado);
            }
        } else {
            logger.info("Id "+ id +" não é uma string base64" );
            ErroPadronizado erroPadronizado = new ErroPadronizado(Resultado.erro(new ApiErrorException("O fingerPrint está em um formato invalido")).getExcecao().getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(erroPadronizado);
        }
    }


    public Boolean isBase64(String base64) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] decode = Base64.getDecoder().decode(base64.getBytes());
            return true;
        } catch(IllegalArgumentException e) {
            return  false;
        }
    }


}
