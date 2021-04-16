package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import br.com.zup.Treinopropostas.Utils.Manager;
import br.com.zup.Treinopropostas.Validations.ErroPadronizado;
import br.com.zup.Treinopropostas.Utils.ApiErrorException;
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
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private final Manager manager;

    private  final SolicitacaoAnaliseResource solicitacaoAnaliseResource;

    public PropostaController(Manager manager, SolicitacaoAnaliseResource solicitacaoAnaliseResource) {
        this.manager = manager;
        this.solicitacaoAnaliseResource = solicitacaoAnaliseResource;
    }

    @PostMapping("/proposta")
    public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {

        if(manager.noExistValue("Proposta", request.getDocumento(), "documento")){
            Proposta proposta = request.toModel();
            manager.salvaEComita(proposta);
            Solicitacao solicitacao;

            try {
                 solicitacao = solicitacaoAnaliseResource.solicitaAnalise(proposta.enviarInformacoes());
            }catch (FeignException.FeignClientException e) {
                    solicitacao = new Solicitacao(StatusCliente.COM_RESTRICAO);
            }

            proposta.atualizaEntidade(solicitacao);
            manager.atualizaEComita(proposta);
            @Valid PropostaResponse response = proposta.toResponse();


            URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
            logger.info("Proposta com id={} com documento={} status ={} ", proposta.getId(), proposta.getDocumento().substring(0,3) + "********", proposta.getStatus());
            return  ResponseEntity.created(uri).body(Resultado.sucesso(response).getSucesso());
        } else {
            Collection<String> mensagens = new ArrayList<>();
            mensagens.add(Resultado.erro(new ApiErrorException("Documento já existente")).getExcecao().getMessage());

            logger.info("Transacao falhou devido a um erro de validação");
            ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(erroPadronizado);
        }

    }

}
