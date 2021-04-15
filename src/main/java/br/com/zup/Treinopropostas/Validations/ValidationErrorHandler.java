package br.com.zup.Treinopropostas.Validations;

import br.com.zup.Treinopropostas.Validations.Utils.ApiErrorException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
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

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
