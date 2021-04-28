package br.com.zup.Treinopropostas.Carteira.Enum;

import java.util.EnumSet;

public enum CarteirasTipo {

    Paypal,SamsungPay;

    public EnumSet<CarteirasTipo> listarAtributos() {
        return EnumSet.allOf(CarteirasTipo.class);
    }

}
