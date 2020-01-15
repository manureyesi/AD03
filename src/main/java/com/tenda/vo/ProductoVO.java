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
    
    private final Long identificador;
    private String descripcion;
    private Double prezo;
    private Long cantidade;

    public ProductoVO(Long identificador, String descripcion, Double prezo, Long cantidade) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.prezo = prezo;
        this.cantidade = cantidade;
    }

    public Long getIdentificador() {
        return identificador;
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

    public Long getCantidade() {
        return cantidade;
    }

    public void setCantidade(Long cantidade) {
        this.cantidade = cantidade;
    }
    
}
