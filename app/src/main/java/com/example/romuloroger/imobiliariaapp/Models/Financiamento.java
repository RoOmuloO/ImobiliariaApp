package com.example.romuloroger.imobiliariaapp.Models;

import java.io.Serializable;

public class Financiamento implements Serializable {

    private double valorFinanciado, valorTotal;
    private int qtdeParcelas, id;

    public Financiamento() {
    }

    public Financiamento(double valorFinanciado, double valorTotal, int qtdeParcelas, int id) {
        this.valorFinanciado = valorFinanciado;
        this.valorTotal = valorTotal;
        this.qtdeParcelas = qtdeParcelas;
        this.id = id;
    }

    public double getValorFinanciado() {
        return valorFinanciado;
    }

    public void setValorFinanciado(double valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getQtdeParcelas() {
        return qtdeParcelas;
    }

    public void setQtdeParcelas(int qtdeParcelas) {
        this.qtdeParcelas = qtdeParcelas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
