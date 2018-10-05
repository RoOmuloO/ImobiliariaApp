package com.example.romuloroger.imobiliariaapp.classes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Moeda {
    private static final Locale BRAZIL = new Locale("pt","BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);
    public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);

    public static String mascaraDinheiro(double valor, DecimalFormat moeda){
        return moeda.format(valor);
    }
}
