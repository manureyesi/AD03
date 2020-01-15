/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.utiles;

/**
 *
 * MenuUtiles
 */
public class MenuUtiles {
    
    public static void mostrarMenu () {
        System.out.println("===================================");
        for (DatosMenuEnum datosMenu : DatosMenuEnum.values()) {
            System.out.println("= " + datosMenu.getIdMenu() + " - " + datosMenu.getDescripcion());
        }
        System.out.println("===================================");
    }
    
}
