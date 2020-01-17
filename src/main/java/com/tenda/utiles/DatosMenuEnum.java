/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.utiles;

/**
 *
 * DatosMenuEnum
 */
public enum DatosMenuEnum {
    
    ENGADIR_TENDA(1, "Engadir Tenda"),
    VER_TENDAS(2, "Engadir Tenda"),
    ELIMINAR_TENDA(3, "Eliminar Tenda"),
    ENGADIR_PRODUCTO(4, "Engadir producto"),
    
    ELIMINAR_PRODUCTO(11, "Eliminar producto"),
    
    
    ENGADIR_EMPREADO_TENDA(6, "Engadir empregado a tenda"),
    ELIMINAR_EMPREADO_TENDA(7, "Eliminar empregado a tenda"),
    ENGADIR_CLIENTE(8, "Engadir cliente"),
    ELIMINAR_CLIENTE(9, "Eliminar cliente"),
    LER_TITULARES_PERIODICO(15, "Ler titulares do periódico"),
    SAIR(16, "Sair");
    
    private final Integer idMenu;
    private final String descripcion;
    
    DatosMenuEnum (final Integer idMenu, final String descipcion) {
        this.idMenu = idMenu;
        this.descripcion = descipcion;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public static DatosMenuEnum buscarPorId (Integer id) {
        for (DatosMenuEnum at : DatosMenuEnum.values()) {
          if (at.getIdMenu().equals(id)) {
            return at;
          }
        }
        return null;
    }
    
}
