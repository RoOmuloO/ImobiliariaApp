package com.example.romuloroger.imobiliariaapp.Models;

import java.io.Serializable;

public class Cliente implements Serializable{

    private int id;
    private String nome, email, telefone;
    private double valorEntrada;

    public Cliente() {
    }

    public Cliente(int id, String nome, String email, String telefone, double valorEntrada) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.valorEntrada = valorEntrada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
