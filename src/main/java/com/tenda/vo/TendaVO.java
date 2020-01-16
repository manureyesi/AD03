/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * TendaVO
 */
public class TendaVO {
    
    private String nome;
    private ProvinciaVO provincia;
    private String cidade;
    
    List<ProductoVO> listaProductos;
    List<EmpregadoVO> listaEmpregados;

    public TendaVO(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
        listaEmpregados = new ArrayList<>();
        listaProductos = new ArrayList<>();
    }
    
    public TendaVO(String nome, String cidade, ProvinciaVO provincia) {
        this.nome = nome;
        this.cidade = cidade;
        this.provincia = provincia;
        listaEmpregados = new ArrayList<>();
        listaProductos = new ArrayList<>();
    }

    public TendaVO(String nome, String cidade, List<ProductoVO> listaProductos, List<EmpregadoVO> listaEmpregados) {
        this.nome = nome;
        this.cidade = cidade;
        this.listaProductos = listaProductos;
        this.listaEmpregados = listaEmpregados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public ProvinciaVO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaVO provincia) {
        this.provincia = provincia;
    }

    public List<ProductoVO> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoVO> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<EmpregadoVO> getListaEmpregados() {
        return listaEmpregados;
    }

    public void setListaEmpregados(List<EmpregadoVO> listaEmpregados) {
        this.listaEmpregados = listaEmpregados;
    }
    
}
