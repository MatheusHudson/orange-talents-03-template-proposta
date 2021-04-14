package br.com.zup.Treinopropostas.Validations;

public class FieldErrorDTO {

    private String campo;
    private String message;

    public FieldErrorDTO(String campo, String message) {
        this.campo = campo;
        this.message = message;
    }

    public String getCampo() {
        return campo;
    }

    public String getMessage() {
        return message;
    }
}
