package br.com.zup.Treinopropostas.Feign;

import br.com.zup.Treinopropostas.Cartao.BloqueioFeignResult;
import br.com.zup.Treinopropostas.Proposta.CartaoIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Map;

@FeignClient(name = "cartao-resource", url = "${CARTAO_RESOURCE_URL:http://localhost:8888}")
public interface CartaoResource {

        @RequestMapping(method = RequestMethod.GET, value = "/api/cartoes", consumes = "application/json")
        CartaoIdResponse getCartao(Map<String, Long> idProposta);

        @RequestMapping(method = RequestMethod.POST, value = "/api/cartoes/{id}/bloqueios", consumes = "application/json")
        BloqueioFeignResult bloquearCartao(@PathVariable("id") String id,Map<String,String> request);
}

