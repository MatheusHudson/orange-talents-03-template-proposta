package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Meters.Metricas;
import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;
import br.com.zup.Treinopropostas.Feign.SolicitacaoAnaliseResource;
import br.com.zup.Treinopropostas.Utils.ApiErrorException;
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
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class CriarPropostaController {

    private final Logger logger = LoggerFactory.getLogger(CriarPropostaController.class);

    private  PropostaRepository repository;

    private Solicitacao solicitacao;

    private final Metricas metricas;

    public CriarPropostaController(PropostaRepository repository, Metricas metricas, SolicitacaoAnaliseResource solicitacaoAnaliseResource) {
        this.repository = repository;
        this.metricas = metricas;
        this.solicitacaoAnaliseResource = solicitacaoAnaliseResource;
    }

    private  final SolicitacaoAnaliseResource solicitacaoAnaliseResource;


    @PostMapping("/proposta")
    public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {

        if(!repository.existsByDocumento(request.getDocumento().replaceAll("[^0-9]", ""))){
            Proposta proposta = request.toModel();
            repository.save(proposta);
            metricas.meuContador();

            try {
                 solicitacao = solicitacaoAnaliseResource.solicitaAnalise(proposta.enviarInformacoes());
            }catch (FeignException.FeignClientException e) {
                    solicitacao = new Solicitacao(StatusCliente.COM_RESTRICAO);
            }

            proposta.atualizaEntidade(solicitacao);
            repository.save(proposta);
            PropostaResponse response = proposta.toResponse();


            URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
            logger.info("Proposta com id={} com documento={} status ={} ", proposta.getId(), proposta.getDocumento().substring(0,3) + "********", proposta.getStatus());
            return  ResponseEntity.created(uri).body(Resultado.sucesso(response).getSucesso());

        } else {
            Collection<String> mensagens = new ArrayList<>();
            mensagens.add(Resultado.erro(new ApiErrorException("Documento já existente")).getExcecao().getMessage());

            ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
            logger.info("Erro 422 ao realizar validação");

            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(erroPadronizado);
        }

    }
}
