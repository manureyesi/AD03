/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.main;

import com.rss.elPais.LeerRSSElPais;
import com.tenda.sqlLite.ClientesRepositorio;
import com.tenda.sqlLite.DBUtiles;
import com.tenda.sqlLite.ProductosRepositorio;
import com.tenda.sqlLite.ProvinciasRepositorio;
import com.tenda.sqlLite.StockRepositorio;
import com.tenda.sqlLite.TendasRepositorio;
import com.tenda.utiles.DatosMenuEnum;
import com.tenda.utiles.MenuUtiles;
import com.tenda.utiles.UtilesTendaJson;
import com.tenda.vo.ClienteVO;
import com.tenda.vo.EmpregadoVO;
import com.tenda.vo.ProductoStockVO;
import com.tenda.vo.ProductoVO;
import com.tenda.vo.ProgramaVO;
import com.tenda.vo.ProvinciaVO;
import com.tenda.vo.TendaVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * Tenda
 */
public class Tenda {

    private static final Scanner sc = new Scanner (System.in);
    
    private static final String NOMBRE_ARCHIVO_SQL_LITE = "tenda.db";
    
    private static Connection connection = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        //Cargar Conexion DB
        connection = DBUtiles.conectarseDB(NOMBRE_ARCHIVO_SQL_LITE);
        
        ProgramaVO datosPrograma;
        TendaVO tenda = null;
        
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
                        
                        tenda = TendasRepositorio.buscarTendaPorNome(connection, nomeTenda);
                        if (tenda == null) {
                            
                            System.out.print("Provincia: ");
                            String nomeProvincia = sc.nextLine();
                            
                            //Buscar si existe Provincia
                            ProvinciaVO provincia = 
                                    ProvinciasRepositorio.buscarProvinciaPorNombre(connection, nomeProvincia);
                            
                            if (provincia != null) {
                                //En caso de non existir Tenda crease
                                TendasRepositorio.insertarDatos(connection, new TendaVO(nomeTenda, cidadeTenda, provincia));
                            } else {
                                System.err.println("Error al buscar Provincia.");
                            }
                            
                        } else {
                            System.err.println("Tenda duplicada, non se creara.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case VER_TENDAS:
                        
                        //Ver Tendas
                        System.out.println("Lista de Tiendas:");
                        
                        for (TendaVO tendaAux : TendasRepositorio.buscarTendas(connection)) {
                            //Datos tiendas
                            System.out.print("Nombre: ");
                            System.out.println(tendaAux.getNome());
                            
                            System.out.print("Cidade: ");
                            System.out.println(tendaAux.getCidade());
                            
                            System.out.print("  Codigo Provincia: ");
                            System.out.println(tendaAux.getProvincia().getId());
                            
                            System.out.print("  Nombre Provincia: ");
                            System.out.println(tendaAux.getProvincia().getNome());
                            System.out.println("");
                            
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case ELIMINAR_TENDA:
                        
                        //Buscar Tenda a eliminar
                        System.out.print("Nome Tenda: ");
                        
                        //Buscar Tenda
                        TendaVO datosTenda = buscarTenda();
                        
                        if (datosTenda != null) {
                            if (comprobarBorrado()) {
                                TendasRepositorio.eliminar(connection, datosTenda.getNome());
                            }
                        } else {
                            System.err.println("A tenda a borrar non existe.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case VER_PRODUCTOS:
                        
                        List<ProductoVO> listaProductos = 
                                ProductosRepositorio.listarProductos(connection);
                        
                        //Ver Tendas
                        System.out.println("Lista de Productos:");
                        
                        for (ProductoVO producto : listaProductos) {
                            //Datos Productos
                            System.out.print("Nombre: ");
                            System.out.println(producto.getNome());
                            
                            System.out.print("Descripcion: ");
                            System.out.println(producto.getDescripcion());
                            
                            System.out.print("Precio: ");
                            System.out.println(producto.getPrezo());
                            System.out.println("");
                            
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case VER_PRODUCTOS_TENDA:
                        
                        System.out.println("Buscar tenda para ver Productos:");
                        System.out.print("Nome Tenda: ");
                       
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            
                            List<ProductoStockVO> listaProductosStock = 
                                    StockRepositorio.buscarStockTenda(connection, tenda.getNome());
                            
                            //Ver Tendas
                            System.out.println("Lista de Productos:");

                            for (ProductoStockVO productoStock : listaProductosStock) {
                                //Datos Productos
                                System.out.print("Nombre: ");
                                System.out.println(productoStock.getNome());

                                System.out.print("Descripcion: ");
                                System.out.println(productoStock.getDescripcion());

                                System.out.print("Precio: ");
                                System.out.println(productoStock.getPrezo());
                                
                                System.out.print("Stock: ");
                                System.out.println(productoStock.getStock());
                                System.out.println("");

                            }

                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case ENGADIR_PRODUCTOS_TENDA:
                        
                        System.out.println("Buscar tenda para engadir Productos:");
                        System.out.print("Nome Tenda: ");
                       
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            
                            System.out.println("Buscar producto para engadir stock:");
                            String nomeProducto = sc.nextLine();
                            
                            ProductoVO productoVO = buscarProducto(nomeProducto);
                            
                            if (productoVO != null) {
                                
                                System.out.print("Stock Producto: ");
                                String stockProducto = sc.nextLine();
                                
                                try {
                                    
                                    ProductoStockVO productoStock = new ProductoStockVO(
                                            Integer.valueOf(stockProducto),
                                            productoVO);
                                    
                                    //Insertar datos
                                    StockRepositorio.insertarDatos(connection, tenda, productoStock);
                                    
                                } catch (NumberFormatException e) {
                                    System.err.println("Error ao engadir producto.");
                                }
                                
                            } else {
                                System.out.println("Producto non encontrado.");
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ACTUALIZAR_STOCK_PRODUCTO_TENDA:
                        
                        System.out.println("Buscar tenda para modificar Productos:");
                        System.out.print("Nome Tenda: ");
                       
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            
                            System.out.println("Buscar producto para modificar stock:");
                            String nomeProducto = sc.nextLine();
                            
                            ProductoVO productoVO = buscarProducto(nomeProducto);
                            
                            if (productoVO != null) {
                                
                                System.out.print("Stock Producto: ");
                                String stockProducto = sc.nextLine();
                                
                                try {
                                    
                                    ProductoStockVO productoStock = new ProductoStockVO(
                                            Integer.valueOf(stockProducto),
                                            productoVO);
                                    
                                    //Insertar datos
                                    StockRepositorio.insertarDatos(connection, tenda, productoStock);
                                    
                                } catch (NumberFormatException e) {
                                    System.err.println("Error ao engadir producto.");
                                }
                                
                            } else {
                                System.out.println("Producto non encontrado.");
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case ELIMINAR_EMPREADO:
                        
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
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ENGADIR_PRODUCTO:
                        
                        System.out.print("Nome Producto: ");
                        String nomeProducto = sc.nextLine();

                        System.out.print("Descripcion Producto: ");
                        String decripcionProducto = sc.nextLine();
                        
                        System.out.print("Prezo Producto: ");
                        String prezoProducto = sc.nextLine();

                        try{

                            ProductoVO productoEngadir = 
                                    new ProductoVO(nomeProducto);
                            productoEngadir.setPrezo(Double.valueOf(prezoProducto.replace(',', '.')));
                            productoEngadir.setDescripcion(StringUtils.isNotBlank(decripcionProducto) ? decripcionProducto : null);
                            
                            if (buscarProducto(nomeProducto)==null) {
                                ProductosRepositorio.insertarDatos(connection, productoEngadir);
                                System.out.println("Producto engadido correctamente.");
                            } else{
                                System.out.println("El producto ya existe.");
                            }
                            
                        } catch (NumberFormatException e) {
                            System.err.println("Error ao engadir producto.");
                        }
                            
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ELIMINAR_PRODUCTO:
                        
                        System.out.print("Nombre Producto: ");
                        String nombreProducto = sc.nextLine();

                        try {

                            ProductoVO productoAux = buscarProducto(nombreProducto);

                            if (productoAux != null) {
                                if (comprobarBorrado()) {
                                   ProductosRepositorio.eliminar(connection, productoAux.getNome());
                                }

                            } else {
                                System.out.println("Producto non encontrado.");
                            }

                        } catch (NumberFormatException e) {
                            System.err.println("Error ao buscar producto a borrar.");
                        }
                            
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
                        
                        ClienteVO cliente = new ClienteVO(
                                nomeCliente, 
                                apelidoCliente, 
                                emailCliente);
                        
                        if (ClientesRepositorio.buscarPorNombre(connection, cliente) == null) {
                            //Añadir cliente
                            ClientesRepositorio.insertarDatos(connection, cliente);
                            System.out.println("Cliente añadido con exito.");
                        } else {
                            System.out.println("El cliente ya existe.");
                        }
                        
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
                    
                    case SAIR:
                        System.err.println("Saindo do programa.");
                        break;
                        
                    default:
                        throw new NumberFormatException();
                    
                }
                
            } catch (NumberFormatException | NullPointerException e) {
                System.err.println("O número introducido non é valido.");
                posicionMenu = 0;
            } catch (SQLException e) {
                posicionMenu = 0;
                System.err.println("Error.");
            }
            
        }
                
    }
    
    private static TendaVO buscarTenda () throws SQLException {
    
        sc.nextLine();
        String nomeTenda = sc.nextLine();

        //Buscar Tenda        
        return TendasRepositorio.buscarTendaPorNome(connection, nomeTenda);
        
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
    
    private static ProductoVO buscarProducto(final String nomeProducto) throws SQLException {
        
        return ProductosRepositorio.buscarPorNombre(connection, nomeProducto);
        
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