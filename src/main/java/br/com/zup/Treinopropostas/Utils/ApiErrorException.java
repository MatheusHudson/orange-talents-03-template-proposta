package br.com.zup.Treinopropostas.Utils;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException {

    private final String message;
    private HttpStatus status;

    public ApiErrorException( String message) {
        super(message);
        this.message = message;
    }

    public ApiErrorException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}