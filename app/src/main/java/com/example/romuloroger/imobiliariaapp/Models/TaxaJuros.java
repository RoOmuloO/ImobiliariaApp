package com.example.romuloroger.imobiliariaapp.Models;

import com.example.romuloroger.imobiliariaapp.classes.Moeda;

import java.io.Serializable;
import java.text.DecimalFormat;

public class TaxaJuros implements Serializable{

    private int id;
    private double valorInicial, valorFinal, txJuros;

    Moeda m = new Moeda();

    public TaxaJuros() {
    }

    public TaxaJuros(int id, double valorInicial, double valorFinal, double txJuros) {
        this.id = id;
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.txJuros = txJuros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorInicial() {
        DecimalFormat formato = new DecimalFormat("0.##");
        valorInicial = Double.valueOf(formato.format(valorInicial));
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getValorFinal() {
        DecimalFormat formato = new DecimalFormat("0.##");
        valorFinal = Double.valueOf(formato.format(valorFinal));
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public double getTaxaJuros() {
        return txJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.txJuros = taxaJuros;
    }

    @Override
    public String toString() {
        return "De: "+m.mascaraDinheiro(getValorInicial(),m.DINHEIRO_REAL)+ "\nAté: "+m.mascaraDinheiro(getValorFinal(),m.DINHEIRO_REAL)+"\nTaxa: "+getTaxaJuros()+"%";
    }
}
