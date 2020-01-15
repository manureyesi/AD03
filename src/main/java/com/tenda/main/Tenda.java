/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.main;

import com.rss.elPais.LeerRSSElPais;
import com.tenda.utiles.DatosMenuEnum;
import com.tenda.utiles.MenuUtiles;
import com.tenda.utiles.UtilesTendaJson;
import com.tenda.vo.ClienteVO;
import com.tenda.vo.EmpregadoVO;
import com.tenda.vo.ProductoVO;
import com.tenda.vo.ProgramaVO;
import com.tenda.vo.TendaVO;
import java.util.Scanner;

/**
 *
 * Tenda
 */
public class Tenda {

    private static final Scanner sc = new Scanner (System.in);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        ProgramaVO datosPrograma;
        
        //Variables Programa
        Integer idTenda;
        
        Integer posicionMenu = 0;
        
        //Cargar menu
        datosPrograma = UtilesTendaJson.cargarDeArchivoDatosTenda();
        
        while (!posicionMenu.equals(DatosMenuEnum.SAIR.getIdMenu())) {
            
            //Pintar Menu
            MenuUtiles.mostrarMenu();
            
            try {
                
                posicionMenu = sc.nextInt();
                
                switch (DatosMenuEnum.buscarPorId(posicionMenu)) {
                    
                    case ENGADIR_TENDA:
                        
                        //Engadir tenda                        
                        System.out.print("Nome: ");
                        sc.nextLine();
                        String nomeTenda = sc.nextLine();
                        
                        System.out.print("Cidade: ");
                        String cidadeTenda = sc.nextLine();
                        
                        datosPrograma.getListaTendas().add(new TendaVO(nomeTenda, cidadeTenda));
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case ELIMINAR_TENDA:
                        
                        //Buscar Tenda a eliminar
                        System.out.print("Nome Tenda: ");
                        
                        //Buscar Tenda
                        idTenda = buscarTenda(datosPrograma);
                        
                        if (idTenda != null) {
                            if (comprobarBorrado()) {
                                datosPrograma.getListaTendas().remove(idTenda.intValue());
                            }
                        } else {
                            System.err.println("A tenda a borrar non existe.");
                        }
                        
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case ENGADIR_EMPREADO_TENDA:
                        
                        System.out.println("Buscar tenda para introducir Empregado:");
                        System.out.print("Nome Tenda: ");
                       
                        idTenda = buscarTenda(datosPrograma);
                        
                        if (idTenda != null) {
                            System.out.println("Tenda encontrada, engadir Emregado:");
                            
                            System.out.print("Nome Empregado: ");
                            String nomeEmpregado = sc.nextLine();
                            
                            System.out.print("Apelido Empregado: ");
                            String apelidoEmpregado = sc.nextLine();
                            
                            System.out.print("Email Empregado: ");
                            String emailEmpregado = sc.nextLine();
                            
                            datosPrograma.getListaTendas().get(idTenda).getListaEmpregados().add(
                                    new EmpregadoVO(nomeEmpregado, apelidoEmpregado, emailEmpregado));
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ELIMINAR_EMPREADO_TENDA:
                        
                        System.out.println("Buscar tenda para eliminar Empregado:");
                        System.out.print("Nome Tenda: ");
                        
                        idTenda = buscarTenda(datosPrograma);
                        
                        if (idTenda != null) {
                            System.out.println("Tenda encontrada, buscar empregado a eliminar Empregado:");
                            
                            System.out.print("Nome Empregado: ");
                            String nomeEmpregado = sc.nextLine();
                            
                            System.out.print("Apelido Empregado: ");
                            String apelidoEmpregado = sc.nextLine();
                            
                            Integer idEmpregadoBorrar = buscarEmpregado(datosPrograma, idTenda, nomeEmpregado, apelidoEmpregado);
                            
                            if (idEmpregadoBorrar!=null) {
                            
                                if (comprobarBorrado()) {
                                    datosPrograma.getListaTendas().get(idTenda).getListaEmpregados().remove(idEmpregadoBorrar.intValue());
                                }
                                
                            } else {
                                System.out.println("Producto non encontrado");
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ENGADIR_PRODUCTO_TENDA:
                        
                        System.out.println("Buscar tenda para engadir producto:");
                        System.out.print("Nome Tenda: ");
                        
                        idTenda = buscarTenda(datosPrograma);
                        
                        if (idTenda != null) {
                            System.out.println("Tenda encontrada, preparandose para introducir producto:");
                            
                            System.out.print("Codigo Producto: ");
                            String codProducto = sc.nextLine();
                            
                            System.out.print("Nome Producto: ");
                            String nomeProducto = sc.nextLine();
                        
                            System.out.print("Pezo Producto: ");
                            String prezoProducto = sc.nextLine();
                            
                            System.out.print("Stock Producto: ");
                            String stockProducto = sc.nextLine();
                            
                            try{
                            
                                datosPrograma.getListaTendas().get(idTenda).getListaProductos().add(
                                        new ProductoVO(
                                                Long.valueOf(codProducto),
                                                nomeProducto,
                                                Double.valueOf(prezoProducto.replace(',', '.')),
                                                Long.valueOf(stockProducto)));
                                
                                System.out.println("Producto engadido correctamente.");
                                
                            } catch (NumberFormatException e) {
                                System.err.println("Error ao engadir producto.");
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ELIMINAR_PRODUCTO_TENDA:
                        
                        System.out.println("Buscar tenda para eliminar Empregado:");
                        System.out.print("Nome Tenda: ");
                        
                        idTenda = buscarTenda(datosPrograma);
                        
                        if (idTenda != null) {
                            System.out.println("Tenda encontrada, preparandose para introducir producto:");
                            
                            System.out.print("Codigo Producto: ");
                            String codProducto = sc.nextLine();
                                                        
                            try {
                            
                                Integer indiceProducto = buscarProducto(datosPrograma, idTenda, Long.valueOf(codProducto));
                            
                                if (indiceProducto != null) {
                                    if (comprobarBorrado()) {
                                        datosPrograma.getListaTendas().get(idTenda).getListaProductos().remove(indiceProducto.intValue());
                                    }
                                    
                                } else {
                                    System.out.println("Producto non encontrado.");
                                }
                                
                            } catch (NumberFormatException e) {
                                System.err.println("Error ao buscar producto a borrar.");
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ENGADIR_CLIENTE:
                        
                        System.out.println("Engair Cliente:");
                        
                        System.out.print("Nome Cliente: ");
                        sc.nextLine();
                        String nomeCliente = sc.nextLine();

                        System.out.print("Apelido Cliente: ");
                        String apelidoCliente = sc.nextLine();

                        System.out.print("Email Cliente: ");
                        String emailCliente = sc.nextLine();
                        
                        datosPrograma.getListaClientes().add(
                                new ClienteVO(
                                    nomeCliente,
                                    apelidoCliente,
                                    emailCliente));
                        
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ELIMINAR_CLIENTE:
                        
                        System.out.println("Buscar cliente a eliminar:");
                        
                        System.out.print("Nome Cliente: ");
                        sc.nextLine();
                        String nomeClienteEliminar = sc.nextLine();

                        System.out.print("Apelido Cliente: ");
                        String apelidoClienteEliminar = sc.nextLine();
                        
                        Integer idClienteElinar = null;
                        
                        for (int i = 0; i < datosPrograma.getListaClientes().size(); i++) {
                            if (nomeClienteEliminar.equalsIgnoreCase(datosPrograma.getListaClientes().get(i).getNome()) && 
                                    apelidoClienteEliminar.equalsIgnoreCase(datosPrograma.getListaClientes().get(i).getApelidos())) {
                                idClienteElinar = i;
                            }
                        }
                        
                        if (idClienteElinar != null ) {
                            if (comprobarBorrado()) {
                                datosPrograma.getListaClientes().remove(idClienteElinar.intValue());
                            }
                         
                        } else {
                            System.out.println("Cliente non encontrado");
                        }
                        
                        UtilesTendaJson.guardarDatosTendaEnArchivo(datosPrograma);
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case LER_TITULARES_PERIODICO:
                        
                        LeerRSSElPais.leerPortadaElPais();
                        
                        pararProgramaAtaEnter();
                        sc.nextLine();
                        
                        break;
                    
                    case CREAR_COPIA_SEGURIDADE:
                        
                        System.out.println("Guardando Backup datos Programa:");
                        System.out.print("Nombre Archivo (Automatico sufixo .backup): ");
                        
                        sc.nextLine();
                        String nomeCopiaSeguridade = sc.nextLine();
                        
                        UtilesTendaJson.gardarBackUpArchivo(datosPrograma, nomeCopiaSeguridade);
                        
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case SAIR:
                        System.err.println("Saindo do programa.");
                        break;
                        
                    default:
                        throw new NumberFormatException();
                    
                }
                
            } catch (NumberFormatException | NullPointerException e) {
                System.err.println("O número introducido non é valido.");
                posicionMenu = 0;
            }
            
        }
                
    }
    
    private static Integer buscarTenda (final ProgramaVO datosPrograma) {
    
        sc.nextLine();
        String nomeTenda = sc.nextLine();

        //Buscar Tenda
        Integer idTenda = UtilesTendaJson.buscarPosicionTenda(nomeTenda, datosPrograma);
        
        return idTenda;
        
    }
    
    private static Integer buscarEmpregado (final ProgramaVO datosPrograma, final Integer idTenda, final String nome, final String apellido) {
    
        Integer idEmpregadoBorrar = null;
        
        for (int i = 0; i < datosPrograma.getListaTendas().size() ; i++) {
            if (datosPrograma.getListaTendas().get(idTenda).getListaEmpregados().get(i).getNome().equalsIgnoreCase(nome) &&
                    datosPrograma.getListaTendas().get(idTenda).getListaEmpregados().get(i).getApelidos().equalsIgnoreCase(apellido)) {
                idEmpregadoBorrar = i;
                break;
            }
        }
        
        return idEmpregadoBorrar;
        
    }
    
    private static void pararProgramaAtaEnter () {
        
        //Parar Ejecucion ata enter
        System.out.println("Presione Enter para continuar.");
        sc.nextLine();
        
    }
    
    private static Integer buscarProducto(final ProgramaVO datosPrograma, final Integer idTenda, final Long codProducto) {
        
        Integer indiceProducto = null;
        
        for (int i = 0; i < datosPrograma.getListaTendas().get(idTenda).getListaProductos().size() ; i++) {
            if (datosPrograma.getListaTendas().get(idTenda).getListaProductos().get(i).getIdentificador().equals(codProducto)) {
                indiceProducto = i;
                break;
            }
        }
        return indiceProducto;
    }
    
    private static Boolean comprobarBorrado () {
    
        Boolean permitirBorrado = Boolean.FALSE;
        
        System.out.println("Seguro que desexa borrar (S/N)?");
                            
        String comprobarBorrar = sc.nextLine();
        
        if (!comprobarBorrar.equalsIgnoreCase("N")) {
            
            System.out.println("Borrado Correctamente.");
            permitirBorrado = Boolean.TRUE;
            
        } else {

            System.out.println("Cancelando Borrado.");

        }
        
        return permitirBorrado;
        
    }
}