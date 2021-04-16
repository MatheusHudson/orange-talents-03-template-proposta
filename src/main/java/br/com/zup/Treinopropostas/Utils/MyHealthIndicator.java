package br.com.zup.Treinopropostas.Utils;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("descrição", "Propoosta aplicação!");
        details.put("endereço", "127.0.0.1:8080");

        return Health.status(Status.UP).withDetails(details).build();
    }

}