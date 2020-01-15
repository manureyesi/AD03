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
    ELIMINAR_TENDA(2, "Eliminar Tenda"),
    ENGADIR_PRODUCTO_TENDA(3, "Engadir producto a unha tenda"),
    ELIMINAR_PRODUCTO_TENDA(4, "Eliminar producto a unha tenda"),
    ENGADIR_EMPREADO_TENDA(5, "Engadir empregado a tenda"),
    ELIMINAR_EMPREADO_TENDA(6, "Eliminar empregado a tenda"),
    ENGADIR_CLIENTE(7, "Engadir cliente"),
    ELIMINAR_CLIENTE(8, "Eliminar cliente"),
    CREAR_COPIA_SEGURIDADE(9, "Crear copia de seguridade"),
    LER_TITULARES_PERIODICO(10, "Ler titulares do periódico"),
    SAIR(11, "Sair");
    
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
