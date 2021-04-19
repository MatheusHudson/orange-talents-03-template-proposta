package br.com.zup.Treinopropostas.Proposta;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "solicitacao-analise-resource", url = "${SOLICITAO_ANALISE}:http://localhost:9999")
interface SolicitacaoAnaliseResource {

        @RequestMapping(method = RequestMethod.POST, value = "/api/solicitacao", consumes = "application/json")
        Solicitacao solicitaAnalise(Solicitacao solicitacao);
}
