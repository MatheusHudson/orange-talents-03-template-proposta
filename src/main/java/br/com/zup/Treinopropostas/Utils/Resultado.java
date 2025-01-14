package br.com.zup.Treinopropostas.Utils;

import org.springframework.util.Assert;

public class Resultado<E extends Exception,S> {

    private S sucesso;

    private E excecao;

    public boolean temErro(){
        return excecao != null;
    }

    public E getExcecao(){
        Assert.isTrue(temErro(),"Não deveria chamar caso não tenha erro");
        return excecao;
    }

    public S getSucesso(){
        Assert.isTrue(!temErro(),"Não deveria chamar caso tenha erro");
        return sucesso;
    }

    public static <T> Resultado<Exception, T> sucesso(T objeto) {
        Resultado<Exception, T> resultado = new Resultado<Exception,T>();
        resultado.sucesso = objeto;
        return resultado;
    }

    public static <E extends Exception,T> Resultado<E, T> erro(E excecao) {
        Resultado<E, T> resultado = new Resultado<E,T>();
        resultado.excecao = excecao;
        return resultado;
    }
}

