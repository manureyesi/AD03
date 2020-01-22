/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.vo;

import java.util.List;

/**
 *
 * EmpregadoVO
 */
public class EmpregadoVO {
 
    private final String nome;
    private final String apelidos;
    private String email;
    private List<HorasEmpregadoVO> listaHoras;
    
    public EmpregadoVO(String nome, String apelidos, String email) {
        this.nome = nome;
        this.apelidos = apelidos;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getApelidos() {
        return apelidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<HorasEmpregadoVO> getListaHoras() {
        return listaHoras;
    }

    public void setListaHoras(List<HorasEmpregadoVO> listaHoras) {
        this.listaHoras = listaHoras;
    }
    
}
