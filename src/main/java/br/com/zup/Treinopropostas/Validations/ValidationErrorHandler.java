package br.com.zup.Treinopropostas.Validations;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

        private final MessageSource messageSource;

    public ValidationErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public List<FieldErrorDTO> handlerValidationMethodArgument(MethodArgumentNotValidException e) {
            List<FieldErrorDTO> errorList = new ArrayList<FieldErrorDTO>();

             e.getBindingResult().getFieldErrors().forEach(erro -> {
                    errorList.add(new FieldErrorDTO(erro.getField(), getErrorMessage(erro)));
             });
             return errorList;
        }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErroPadronizado handlerValidationConstraintViolationException(ConstraintViolationException e) {

        return erroPadronizado(e.getMessage().substring(25));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public ErroPadronizado handlerValidationDateTimeParseException(DateTimeParseException e) {
        return  erroPadronizado("Inserir a data no formato yyyy/MM/dd !");
    }



    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

    private ErroPadronizado erroPadronizado(String mensagem) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(mensagem);
        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return erroPadronizado;
    }
}
