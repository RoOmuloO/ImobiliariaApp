package com.example.romuloroger.imobiliariaapp.Models;

import com.example.romuloroger.imobiliariaapp.Enums.ETipoUsuario;

import java.io.Serializable;

public class Usuario implements Serializable{

    private int id;
    private String nome, login, cpf, email, senha, confirmarSenha;
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(int id, String nome, String login, String cpf, String email, String senha, String confirmarSenha, String tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.tipoUsuario = tipoUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
}
