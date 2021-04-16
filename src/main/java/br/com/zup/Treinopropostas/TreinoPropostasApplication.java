package br.com.zup.Treinopropostas;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class TreinoPropostasApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinoPropostasApplication.class, args);
	}


	@Bean
	Logger.Level feignLoggerLevel() { return Logger.Level.FULL;
	}

}
