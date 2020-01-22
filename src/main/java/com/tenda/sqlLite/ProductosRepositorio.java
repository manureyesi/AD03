package com.tenda.sqlLite;

import com.tenda.vo.ProductoVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * ProvinciasRepositorio
 */
public class ProductosRepositorio {
        
    /*
    Metodo para crear Objeto
    */
    private static ProductoVO crearObjeto (final ResultSet rs) throws SQLException {
    
        ProductoVO producto = new ProductoVO(rs.getString("NOME"));
        producto.setDescripcion(rs.getString("DESCRIPCION") != null ? rs.getString("DESCRIPCION") : null);
        producto.setPrezo(rs.getDouble("PREZO"));
        return producto;
    }
    
    /*
    Método para crear DB de Provincias
    */
    static void crearTabla (final Connection con) throws SQLException {
        
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE IF NOT EXISTS ");
        createSql.append(ConstantesDB.TABLA_PRODUCTOS);
        createSql.append(" (NOME VARCHAR(30) PRIMARY KEY, ");
        createSql.append("DESCRIPCION VARCHAR(100), ");
        createSql.append("PREZO DOUBLE NOT NULL);");
        
        Statement stmt = con.createStatement();
        stmt.execute(createSql.toString());
        
    }
    
    /*
    Método para insertar datos en la tabla
    */
    public static void insertarDatos (final Connection con, final ProductoVO producto) throws SQLException {
    
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(ConstantesDB.TABLA_PRODUCTOS);
        insertSql.append(" ( ");
        insertSql.append("NOME, PREZO");
        insertSql.append(producto.getDescripcion() != null ? ", DESCRIPCION" : "");
        insertSql.append(" ) ");
        insertSql.append("VALUES(?, ?");
        insertSql.append(producto.getDescripcion() != null ? ", ?" : "");
        insertSql.append(");");
        
        PreparedStatement pstmt = con.prepareStatement(insertSql.toString());

        //Aquí e cando engadimos o valor do nome
        pstmt.setString(1, producto.getNome());
        pstmt.setDouble(2, producto.getPrezo());
        if (producto.getDescripcion() != null) {
            pstmt.setString(3, producto.getDescripcion());
        }
        pstmt.executeUpdate();
        
    }
    
    /*
    Método para buscar en la tabla los productos
    */
    public static List<ProductoVO> listarProductos (final Connection con) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_PRODUCTOS);
        selectSql.append(" where 1 = 1;");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        List<ProductoVO> listaProductos = new ArrayList<>();
        
        while(rs.next()){
            //Crear Objeto
            listaProductos.add(crearObjeto(rs));
        }
        
        return listaProductos;
    }
    
    /*
    Método para buscar en la tabla por nombre
    */
    public static ProductoVO buscarPorNombre (final Connection con, final String nombreProducto) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_PRODUCTOS);
        selectSql.append(" where NOME = '");
        selectSql.append(nombreProducto);
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        ProductoVO producto = null;
        
        while(rs.next()){
            //Crear Objeto
            producto = crearObjeto(rs);
        }
        
        return producto;
    }
    
    /*
    Método para eliminar en la tabla por nombre
    */
    public static void eliminar (final Connection con, final String nomeProducto) throws SQLException {
    
        StockRepositorio.eliminar(con, null, nomeProducto);
        
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("DELETE FROM ");
        deleteSql.append(ConstantesDB.TABLA_PRODUCTOS);
        deleteSql.append(" where NOME = '");
        deleteSql.append(nomeProducto);
        deleteSql.append("';");
        
        con.prepareStatement(deleteSql.toString()).executeUpdate();
        
    }
    
}
