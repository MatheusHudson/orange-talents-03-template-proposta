package br.com.zup.Treinopropostas.Utils;

public class ApiErrorException extends RuntimeException {

    private final String message;

    public ApiErrorException( String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}