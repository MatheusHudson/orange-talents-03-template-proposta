package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import br.com.zup.Treinopropostas.Utils.Resultado;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;



import javax.validation.Valid;
import java.net.URI;

@RestController
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private  PropostaRepository repository;

    private Solicitacao solicitacao;

    public PropostaController( PropostaRepository repository, SolicitacaoAnaliseResource solicitacaoAnaliseResource) {
        this.repository = repository;
        this.solicitacaoAnaliseResource = solicitacaoAnaliseResource;
    }

    private  final SolicitacaoAnaliseResource solicitacaoAnaliseResource;


    @PostMapping("/proposta")
    public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {

        if(!repository.existValue(request.getDocumento())){
            Proposta proposta = request.toModel();

            repository.save(proposta);

            try {
                 solicitacao = solicitacaoAnaliseResource.solicitaAnalise(proposta.enviarInformacoes());
            }catch (FeignException.FeignClientException e) {
                    solicitacao = new Solicitacao(StatusCliente.COM_RESTRICAO);
            }

            proposta.atualizaEntidade(solicitacao);
            repository.save(proposta);
            @Valid PropostaResponse response = proposta.toResponse();


            URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
            logger.info("Proposta com id={} com documento={} status ={} ", proposta.getId(), proposta.getDocumento().substring(0,3) + "********", proposta.getStatus());
            return  ResponseEntity.created(uri).body(Resultado.sucesso(response).getSucesso());
        } else {
            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ErroPadronizado.repostaErro("Erro 422 ao realizar validação", "Documento já existente",logger));
        }

    }

}
