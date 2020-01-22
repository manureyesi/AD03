/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.vo;

/**
 *
 * HorasEmpregadoVO
 */
public class HorasEmpregadoVO {
    
    private final Integer horas;
    private final TendaVO tenda;

    public HorasEmpregadoVO(Integer horas, TendaVO tenda) {
        this.horas = horas;
        this.tenda = tenda;
    }

    public Integer getHoras() {
        return horas;
    }

    public TendaVO getTenda() {
        return tenda;
    }
    
}
