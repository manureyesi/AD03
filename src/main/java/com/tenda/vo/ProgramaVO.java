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
 * ProgramaVO
 */
public class ProgramaVO {
    
    private List<TendaVO> listaTendas;
    private List<ClienteVO> listaClientes;

    public ProgramaVO() {
        listaClientes= new ArrayList<>();
        listaTendas = new ArrayList<>();
    }

    public ProgramaVO(ArrayList<TendaVO> listaTendas, ArrayList<ClienteVO> listaClientes) {
        this.listaTendas = listaTendas;
        this.listaClientes = listaClientes;
    }

    public List<TendaVO> getListaTendas() {
        return listaTendas;
    }

    public void setListaTendas(ArrayList<TendaVO> listaTendas) {
        this.listaTendas = listaTendas;
    }

    public List<ClienteVO> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<ClienteVO> listaClientes) {
        this.listaClientes = listaClientes;
    }
    
}
