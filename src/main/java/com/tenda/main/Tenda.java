package com.tenda.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.rss.elPais.LeerRSSElPais;
import com.tenda.sqlLite.ClientesRepositorio;
import com.tenda.sqlLite.DBUtiles;
import com.tenda.sqlLite.EmpregadoRepositorio;
import com.tenda.sqlLite.ListaHorasEmpregadoRepositorio;
import com.tenda.sqlLite.ProductosRepositorio;
import com.tenda.sqlLite.ProvinciasRepositorio;
import com.tenda.sqlLite.StockRepositorio;
import com.tenda.sqlLite.TendasRepositorio;
import com.tenda.utiles.DatosMenuEnum;
import com.tenda.utiles.MenuUtiles;
import com.tenda.vo.ClienteVO;
import com.tenda.vo.EmpregadoVO;
import com.tenda.vo.HorasEmpregadoVO;
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
        
        TendaVO tenda;
        ProvinciaVO provincia;
        ProductoVO producto;
        ProductoStockVO productoStock;
        ClienteVO cliente;
        EmpregadoVO empregado;
                
        Integer posicionMenu = 0;
                
        while (!posicionMenu.equals(DatosMenuEnum.SAIR.getIdMenu())) {
            
            //Pintar Menu
            MenuUtiles.mostrarMenu();
            
            try {
                
                posicionMenu = sc.nextInt();
                
                switch (DatosMenuEnum.buscarPorId(posicionMenu)) {
                    
                    case ENGADIR_TENDA:
                        
                        System.out.println("Añadir Tienda:");
                        
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
                            provincia = 
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
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            if (comprobarBorrado()) {
                                TendasRepositorio.eliminar(connection, tenda.getNome());
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
                        
                        for (ProductoVO productoAux : listaProductos) {
                            //Datos Productos
                            System.out.print("Nombre: ");
                            System.out.println(productoAux.getNome());
                            
                            System.out.print("Descripcion: ");
                            System.out.println(productoAux.getDescripcion());
                            
                            System.out.print("Precio: ");
                            System.out.println(productoAux.getPrezo());
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

                            for (ProductoStockVO productoStockAux : listaProductosStock) {
                                //Datos Productos
                                System.out.print("Nombre: ");
                                System.out.println(productoStockAux.getNome());

                                System.out.print("Descripcion: ");
                                System.out.println(productoStockAux.getDescripcion());

                                System.out.print("Precio: ");
                                System.out.println(productoStockAux.getPrezo());
                                
                                System.out.print("  Stock: ");
                                System.out.println(productoStockAux.getStock());
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
                            
                            producto = buscarProducto(nomeProducto);
                            
                            if (producto != null) {
                                
                                System.out.print("Stock Producto: ");
                                String stockProducto = sc.nextLine();
                                
                                try {
                                    
                                    productoStock = new ProductoStockVO(
                                            Integer.valueOf(stockProducto),
                                            producto);
                                    
                                    //Comprobar se non se ten datos de Stock para esa tenda
                                    if (StockRepositorio.buscarProducto(connection, tenda.getNome(), producto.getNome())==null) {
                                        //Insertar datos
                                        StockRepositorio.insertarDatos(connection, tenda, productoStock);
                                    } else {
                                        System.err.println("Error, o producto xa tiña stock");
                                    }
                                    
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
                            
                            producto = buscarProducto(nomeProducto);
                            
                            if (producto != null) {
                                
                                productoStock =
                                        StockRepositorio.buscarProducto(connection, tenda.getNome(), producto.getNome());
                                
                                if (productoStock != null) {
                                    
                                    System.out.print("Stock Producto ("+productoStock.getStock()+"): ");
                                    String stockProducto = sc.nextLine();

                                    if (StringUtils.isNotBlank(stockProducto)) {
                                        try {
                                            
                                            productoStock.setStock(Integer.valueOf(stockProducto));

                                            //Modificar datos
                                            StockRepositorio.modificar(
                                                    connection,
                                                    productoStock,
                                                    tenda.getNome());

                                        } catch (NumberFormatException e) {
                                            System.err.println("Error ao engadir producto.");
                                        }
                                    }
                                    
                                } else {
                                    System.err.println("Error, o producto non ten stock nesta tenda.");
                                }
                                
                            } else {
                                System.out.println("Producto non encontrado.");
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case MOSTRAR_STOCK_TENDA:
                        
                        System.out.println("Buscar tenda para ver Producto:");
                        System.out.print("Nome Tenda: ");
                       
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            
                            System.out.println("Buscar producto para ver stock:");
                            String nomeProducto = sc.nextLine();
                            
                            producto = buscarProducto(nomeProducto);
                            
                            if (producto != null) {
                                
                                //Buscar Stock Producto
                                productoStock = StockRepositorio.buscarProducto(connection, tenda.getNome(), nomeProducto);
                                
                                //Datos Productos
                                System.out.print("Nombre: ");
                                System.out.println(productoStock.getNome());

                                System.out.print("Descripcion: ");
                                System.out.println(productoStock.getDescripcion());

                                System.out.print("Precio: ");
                                System.out.println(productoStock.getPrezo());
                                
                                System.out.print("  Stock: ");
                                System.out.println(productoStock.getStock());
                                System.out.println("");
                                
                            } else {
                                System.out.println("Producto non encontrado.");
                            } 
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ANADIR_EMPREGADO_TENDA:
                        
                        System.out.println("Añadir empleado a tienda, si no existe se crea:");
                        System.out.print("Nome Tenda: ");
                        
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            System.out.println("Tenda encontrada, introduce email empregado:");
                            
                            System.out.print("Email Empregado: ");
                            String email = sc.nextLine();
                            
                            if (EmpregadoRepositorio.buscar(connection, email) != null) {
                            
                                System.out.println("O empregado existe.");
                                System.out.print("Horas Empregado: ");
                                String horasEmpregado = sc.nextLine();
                                
                                try {
                                
                                    EmpregadoVO empregadoVO = EmpregadoRepositorio.buscar(connection, email);
                                    
                                    //Añadir horas
                                    ListaHorasEmpregadoRepositorio.insertarDatos(
                                            connection,
                                            empregadoVO,
                                            tenda.getNome(),
                                            Integer.valueOf(horasEmpregado));
                                    
                                } catch (NumberFormatException e) {
                                    System.out.println("Formato Hora incorrecto");
                                }
                                
                            } else {
                                System.out.println("O empregado non existe, crearase:");
                                
                                System.out.print("Nome Empregado: ");
                                String nomeEmpregado = sc.nextLine();

                                System.out.print("Apelido Empregado: ");
                                String apelidoEmpregado = sc.nextLine();
                                
                                System.out.print("Horas Empregado: ");
                                String horasEmpregado = sc.nextLine();
                                
                                try {
                                
                                    empregado = new EmpregadoVO(nomeEmpregado, apelidoEmpregado, email);
                                    //Crear empregado
                                    EmpregadoRepositorio.insertarDatos(connection, empregado);
                                    
                                    //Añadir horas
                                    ListaHorasEmpregadoRepositorio.insertarDatos(
                                            connection,
                                            empregado,
                                            tenda.getNome(),
                                            Integer.valueOf(horasEmpregado));
                                    
                                } catch (NumberFormatException e) {
                                    System.out.println("Formato Hora incorrecto");
                                }
                                
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case VER_EMPREGADOS:
                        
                        System.out.println("Lista Empregados:");
                        
                        for (EmpregadoVO empregadoAux : EmpregadoRepositorio.listarEmpregado(connection)) {
                        
                            //Datos Empregado
                            System.out.print("Nombre: ");
                            System.out.println(empregadoAux.getNome());
                            
                            System.out.print("Apelidos: ");
                            System.out.println(empregadoAux.getApelidos());
                            
                            System.out.print("Email: ");
                            System.out.println(empregadoAux.getEmail());
                            
                            System.out.println("[");
                            
                            for (HorasEmpregadoVO horasAux : empregadoAux.getListaHoras()) {
                                
                                System.out.print("  Horas Semanais: ");
                                System.out.println(horasAux.getHoras());
                                
                                System.out.print("  Tenda: ");
                                System.out.println(horasAux.getTenda().getNome());
                                
                            }
                            
                            System.out.println("]");
                            System.out.println("");
                            
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ELIMINAR_EMPREADO:
                        
                        System.out.println("Buscar empregado para eliminar:");
                        
                        System.out.print("Email Empregado: ");
                        String email = sc.nextLine();

                        EmpregadoVO empregadoVO = EmpregadoRepositorio.buscar(connection, email);

                        if (empregadoVO != null) {

                            if (comprobarBorrado()) {
                                EmpregadoRepositorio.eliminar(connection, empregadoVO);
                                System.out.println("Empregado eliminado.");
                            } else {
                                System.out.println("O empregado non existe");
                            }

                        } else {
                            System.out.println("Empregado non encontrado.");                             
                        }
                            
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case ELIMINAR_EMPREADO_TENDA:
                        
                        System.out.println("Buscar tenda para eliminar Empregado:");
                        System.out.print("Nome Tenda: ");
                        
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            System.out.println("Tenda encontrada, introduce email empregado:");
                            
                            System.out.print("Email Empregado: ");
                            String emailEmpregado = sc.nextLine();
                            
                            empregado = EmpregadoRepositorio.buscar(connection, emailEmpregado);
                            
                            if (empregado != null) {
                            
                                if (comprobarBorrado()) {
                                    ListaHorasEmpregadoRepositorio.eliminar(connection, empregado, tenda);
                                    System.out.println("Empregado eliminado.");
                                } else {
                                    System.out.println("O empregado non existe");
                                }
                                
                            } else {
                                System.out.println("Empregado non encontrado.");                             
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case ENGADIR_PRODUCTO:
                        
                        System.out.print("Nome Producto: ");
                        sc.nextLine();
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
                    
                    case ELIMINAR_PRODUCTO_TENDA:
                        
                        System.out.println("Buscar tenda para eliminar Producto:");
                        System.out.print("Nome Tenda: ");
                       
                        tenda = buscarTenda();
                        
                        if (tenda != null) {
                            
                            System.out.println("Buscar producto para eliminar:");
                            nomeProducto = sc.nextLine();
                            
                            producto = buscarProducto(nomeProducto);
                            
                            if (producto != null) {
                                
                                productoStock =
                                        StockRepositorio.buscarProducto(connection, tenda.getNome(), producto.getNome());
                                
                                if (productoStock != null) {
                                    if (comprobarBorrado()) {
                                        StockRepositorio.eliminar(connection, tenda.getNome(), productoStock.getNome());
                                    }
                                    
                                } else {
                                    System.err.println("Error, o producto non ten stock nesta tenda.");
                                }
                                
                            } else {
                                System.out.println("Producto non encontrado.");
                            }
                            
                        } else {
                            System.out.println("Tenda non encontrada.");
                        }
                        
                        pararProgramaAtaEnter();
                        
                        break;
                        
                    case ELIMINAR_PRODUCTO:
                        
                        System.out.print("Nombre Producto: ");
                        String nombreProducto = sc.nextLine();

                        try {

                            producto = buscarProducto(nombreProducto);

                            if (producto != null) {
                                if (comprobarBorrado()) {
                                   ProductosRepositorio.eliminar(connection, producto.getNome());
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
                        
                        cliente = new ClienteVO(
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
                        
                        pararProgramaAtaEnter();
                        
                        break;
                    
                    case MOSTRAR_CLIENTES:
                        
                        //Ver Clientes
                        System.out.println("Lista de Clientes:");

                        for (ClienteVO clienteAux : ClientesRepositorio.listarClientes(connection)) {
                            //Datos Productos
                            System.out.print("Nombre: ");
                            System.out.println(clienteAux.getNome());

                            System.out.print("Apellido: ");
                            System.out.println(clienteAux.getApelidos());

                            System.out.print("Email: ");
                            System.out.println(clienteAux.getEmail());
                            System.out.println("");

                        }
                        
                        break;
                        
                    case ELIMINAR_CLIENTE:
                        
                        System.out.println("Buscar cliente a eliminar:");
                        
                        System.out.print("Nome Cliente: ");
                        sc.nextLine();
                        String nomeClienteEliminar = sc.nextLine();

                        System.out.print("Apelido Cliente: ");
                        String apelidoClienteEliminar = sc.nextLine();
                        
                        ClienteVO clienteEliminar = new ClienteVO(
                                nomeClienteEliminar,
                                apelidoClienteEliminar,
                                null);
                        
                        if (ClientesRepositorio.buscarPorNombre(connection, clienteEliminar) != null) {
                            if (comprobarBorrado()) {
                                ClientesRepositorio.eliminar(connection, clienteEliminar);
                            }
                        } else {
                            System.err.println("El cliente no existe.");
                        }
                        
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