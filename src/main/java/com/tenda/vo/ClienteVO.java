/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.vo;

/**
 *
 * ClienteVO
 */
public class ClienteVO {
    
    private String nome;
    private String apelidos;
    private String email;

    public ClienteVO() {
    }

    public ClienteVO(String nome, String apelidos, String email) {
        this.nome = nome;
        this.apelidos = apelidos;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
