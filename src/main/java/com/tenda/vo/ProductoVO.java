/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.vo;

/**
 *
 * ProductoVO
 */
public class ProductoVO {
    
    private final String nome;
    private String descripcion;
    private Double prezo;

    public ProductoVO(String nome) {
        this.nome = nome;
    }

    public ProductoVO(String nome, String descripcion, Double prezo) {
        this.nome = nome;
        this.descripcion = descripcion;
        this.prezo = prezo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrezo() {
        return prezo;
    }

    public void setPrezo(Double prezo) {
        this.prezo = prezo;
    }

}
