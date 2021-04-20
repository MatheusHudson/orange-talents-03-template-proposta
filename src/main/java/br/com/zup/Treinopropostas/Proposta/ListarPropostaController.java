package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Validations.CPForCNPJ;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
public class ListarPropostaController {

    private final PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(ListarPropostaController.class);

    public ListarPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/proposta/{documento}")
    public ResponseEntity<?> obterProposta(@PathVariable @Valid @CPForCNPJ String documento) {

        Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(documento.replaceAll("[^0-9]", ""));
        if(possivelProposta.isPresent()) {

            Proposta proposta = possivelProposta.get();
            PropostaResponse response = proposta.toResponse();
            logger.info("Exbindo a proposta com o documento ={}.", response.getDocumento().substring(0,3) + "***********");
            return ResponseEntity.ok(response);


        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErroPadronizado.repostaErro("Documento " + documento +" nao foi encontrado.", "Não foi localizada nenhuma proposta para o documento informado",logger));
        }
    }
}
