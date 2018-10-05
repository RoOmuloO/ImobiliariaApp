package com.example.romuloroger.imobiliariaapp.Models;

import com.example.romuloroger.imobiliariaapp.Enums.ETipoImovel;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Imovel implements Serializable {

    private int id, qtdeQuartos;
    private String nome, bairro, descricao;
    private double preco;
    private ETipoImovel tipoImovel;
    private boolean ehVendido;
    private String listaFotos;

    public Imovel() {
    }

    public Imovel(int id, int qtdeQuartos, String nome, String bairro, String descricao, double preco, ETipoImovel tipoImovel, boolean ehVendido, String listaFotos) {
        this.id = id;
        this.qtdeQuartos = qtdeQuartos;
        this.nome = nome;
        this.bairro = bairro;
        this.descricao = descricao;
        this.preco = preco;
        this.tipoImovel = tipoImovel;
        this.ehVendido = ehVendido;
        this.listaFotos = listaFotos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ETipoImovel getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(ETipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public int getQtdeQuartos() {
        return qtdeQuartos;
    }

    public void setQtdeQuartos(int qtdeQuartos) {
        this.qtdeQuartos = qtdeQuartos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {

        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isEhVendido() {
        return ehVendido;
    }

    public void setEhVendido(boolean ehVendido) {
        this.ehVendido = ehVendido;
    }

    public String getListaFotos() {
        return listaFotos;
    }

    public void setListaFotos(String listaFotos) {
        this.listaFotos = listaFotos;
    }
}
