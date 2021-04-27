package br.com.zup.Treinopropostas.Cartao;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destinoViagem;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    @NotNull
    private LocalDate dataTerminoViagem;

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    @Override
    public String toString() {
        return "AvisoViagemRequest{" +
                "destinoViagem='" + destinoViagem + '\'' +
                ", dataTerminoViagem=" + dataTerminoViagem +
                '}';
    }
}
