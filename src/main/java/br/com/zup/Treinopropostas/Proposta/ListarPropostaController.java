package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Utils.ApiErrorException;
import br.com.zup.Treinopropostas.Utils.Resultado;
import br.com.zup.Treinopropostas.Validations.CPForCNPJ;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
public class ListarPropostaController {

    private final PropostaRepository propostaRepository;

    @Value("${my.saltSecret}")
    private String salt;

    private final Logger logger = LoggerFactory.getLogger(ListarPropostaController.class);

    public ListarPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/proposta")
    @Timed(extraTags = {"emissora", "mastercad" , "banco", "itau"}, value = "consultar_proposta")
    public ResponseEntity<?> obterProposta(@RequestBody @Valid GetPropostaRequest request) {

        Optional<Proposta> possivelProposta = propostaRepository.findById(request.getId());

        if (possivelProposta.isPresent()) {
            Proposta proposta = possivelProposta.get();

            String documento = request.getDocumento().replaceAll("[^0-9]", "");
            String decryptDocumento = Encryptors.text(documento, salt).decrypt(proposta.getDocumento());

            if(decryptDocumento.equals(documento) && proposta.getNome().equals(request.getNome()) ){
                PropostaResponse response = proposta.toResponse();

                logger.info("Exbindo a proposta com o id = {}.", proposta.getId());
                return ResponseEntity.ok(response);
            } else {
                ErroPadronizado erroPadronizado = new ErroPadronizado(Resultado.erro(new ApiErrorException("Não foi localizada nenhuma proposta para o documento informado")).getExcecao().getMessage());
                logger.info("Documento " + documento + " invalido para esta requisição.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
            }

        }
        else {
            ErroPadronizado erroPadronizado = new ErroPadronizado(Resultado.erro(new ApiErrorException("Não foi localizada nenhuma proposta para o documento informado")).getExcecao().getMessage());
            logger.info("Documento  nao foi encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadronizado);
        }
    }
}
