package br.com.zup.Treinopropostas.Carteira.Enum;

import java.util.EnumSet;

public enum Carteiras {

    Paypal;

    public EnumSet<Carteiras> listarAtributos() {
        return EnumSet.allOf(Carteiras.class);
    }

}
