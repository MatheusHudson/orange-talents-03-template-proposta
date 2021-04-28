package br.com.zup.Treinopropostas.Validations;

import br.com.zup.Treinopropostas.Carteira.CarteiraRequest;
import br.com.zup.Treinopropostas.Carteira.Enum.CarteirasTipo;
import br.com.zup.Treinopropostas.Utils.ApiErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CarteiraRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CarteiraRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(errors.hasErrors())
            return;
        CarteiraRequest request = (CarteiraRequest) o;
        try{
            CarteirasTipo.valueOf(request.getCarteira());
        }catch (IllegalArgumentException e) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Por favor inserir uma carteira valida ! " + CarteirasTipo.Paypal.listarAtributos());
        }
    }
}