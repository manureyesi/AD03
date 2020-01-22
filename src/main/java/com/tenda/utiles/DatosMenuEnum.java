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
    VER_TENDAS(2, "Ver Tendas"),
    ELIMINAR_TENDA(3, "Eliminar Tenda"),
    ENGADIR_PRODUCTO(4, "Engadir producto"),
    VER_PRODUCTOS(5, "Mostrar os productos da franquicia"),
    VER_PRODUCTOS_TENDA(6, "Mostrar os productos dunha tenda"),
    ENGADIR_PRODUCTOS_TENDA(7, "Engadir un producto a unha tenda"),
    ACTUALIZAR_STOCK_PRODUCTO_TENDA(8, "Actualizar o stock dun producto nunha determinada tenda"),
    MOSTRAR_STOCK_TENDA(9, "Mostrar o stock dun producto dunha tenda"),
    ELIMINAR_PRODUCTO_TENDA(10, "Eliminar un producto dunha determinada tenda"),
    ELIMINAR_PRODUCTO(11, "Eliminar producto"),
    ENGADIR_CLIENTE(12, "Engadir cliente"),
    MOSTRAR_CLIENTES(13, "Mostrar os clientes"),
    ELIMINAR_CLIENTE(14, "Eliminar cliente"),
    ANADIR_EMPREGADO_TENDA(15, "Anadir empregado a tenda"),
    VER_EMPREGADOS(16, "Ver lista Empregados"),
    ELIMINAR_EMPREADO(17, "Eliminar empregado"),
    ELIMINAR_EMPREADO_TENDA(18, "Eliminar empregado dunha Tenda"),
    LER_TITULARES_PERIODICO(19, "Ler titulares do periódico"),
    SAIR(20, "Sair");
    
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
