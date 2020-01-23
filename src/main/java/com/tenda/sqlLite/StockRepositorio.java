package com.tenda.sqlLite;

import com.tenda.vo.ProductoStockVO;
import com.tenda.vo.TendaVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * StockRepositorio
 */
public class StockRepositorio {
    
    /*
    Metodo para crear Objeto
    */
    private static ProductoStockVO crearObjeto (final ResultSet rs, final Connection connection) throws SQLException {
    
        ProductoStockVO producto = new ProductoStockVO(
                rs.getInt("STOCK"),
                ProductosRepositorio.buscarPorNombre(
                        connection,
                        rs.getString("ID_PRODUCTO")));
       
        return producto;
    }
    
    /*
    Método para crear DB de Stock
    */
    public static void crearTabla (final Connection con) throws SQLException {
        
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE IF NOT EXISTS ");
        createSql.append(ConstantesDB.TABLA_STOCK);
        createSql.append("(");
        createSql.append("ID_TENDA VARCHAR(30), ");
        createSql.append("ID_PRODUCTO VARCHAR(30), ");
        createSql.append("STOCK integer NOT NULL, ");
        createSql.append("FOREIGN KEY(ID_TENDA) REFERENCES ");
        createSql.append(ConstantesDB.TABLA_TENDAS);
        createSql.append("(NOME), ");
        createSql.append("PRIMARY KEY(ID_TENDA, ID_PRODUCTO), ");
        createSql.append("FOREIGN KEY(ID_PRODUCTO) REFERENCES ");
        createSql.append(ConstantesDB.TABLA_PRODUCTOS);
        createSql.append("(NOME)");
        createSql.append(");");
        
        Statement stmt = con.createStatement();
        stmt.execute(createSql.toString());
        
    }
    
    /*
    Método para insertar datos en la tabla
    */
    public static void insertarDatos (final Connection con, final TendaVO tenda, final ProductoStockVO producto) throws SQLException {
    
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(ConstantesDB.TABLA_STOCK);
        insertSql.append(" ( ");
        insertSql.append("ID_TENDA, ID_PRODUCTO, STOCK");
        insertSql.append(" ) ");
        insertSql.append("VALUES(?, ?, ?);");
        
        PreparedStatement pstmt = con.prepareStatement(insertSql.toString());

        //Aquí e cando engadimos o valor do nome
        pstmt.setString(1, tenda.getNome());
        pstmt.setString(2, producto.getNome());
        pstmt.setInt(3, producto.getStock());
        pstmt.executeUpdate();
        
    }
    
    /*
    Método para buscar en la tabla por id
    */
    public static List<ProductoStockVO> buscarStockTenda (final Connection con, final String nomeTenda) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_STOCK);
        selectSql.append(" where ID_TENDA = '");
        selectSql.append(nomeTenda);
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        List<ProductoStockVO> listaProductos = new ArrayList<>();
        
        while(rs.next()){
            //Crear Objeto
            listaProductos.add(crearObjeto(rs, con));
        }
        
        return listaProductos;
    }
    
    /*
    Método para buscar en la Tabla Por Tienda y Producto
    */
    public static ProductoStockVO buscarProducto (final Connection con, final String nomeTenda, final String nomeProducto) throws SQLException {
            
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_STOCK);
        selectSql.append(" where ID_TENDA = '");
        selectSql.append(nomeTenda);
        selectSql.append("' and ID_PRODUCTO = '");
        selectSql.append(nomeProducto);
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        ProductoStockVO productoStockVO = null;
        
        while(rs.next()){
            //Crear Objeto
            productoStockVO = crearObjeto(rs, con);
        }
        
        return productoStockVO;
    }
    
    /*
    Método para modificar en la tabla
    */
    public static void modificar (final Connection con, final ProductoStockVO productoStockVO, final String nomeTenda) throws SQLException {
    
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ");
        updateSql.append(ConstantesDB.TABLA_STOCK);
        if (productoStockVO.getStock() !=null) {
            updateSql.append(" SET STOCK = ");
            updateSql.append(productoStockVO.getStock());
        }
        updateSql.append(" WHERE ");
        updateSql.append("ID_PRODUCTO = '");
        updateSql.append(productoStockVO.getNome());
        updateSql.append("' and ID_TENDA = '");
        updateSql.append(nomeTenda);
        updateSql.append("'");
        
        con.createStatement();
        
    }
    
    /*
    Método para eliminar en la tabla por tenda
    */
    public static void eliminar (final Connection con, final String nomeTenda, final String nomeProducto) throws SQLException {
    
        Boolean ponerAnd = Boolean.FALSE;
        
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("DELETE FROM ");
        deleteSql.append(ConstantesDB.TABLA_STOCK);
        deleteSql.append(" where");
        if (StringUtils.isNotBlank(nomeTenda)) {
            deleteSql.append(" ID_TENDA = '");
            deleteSql.append(nomeTenda);
            deleteSql.append("'");
            ponerAnd = Boolean.TRUE;
        }
        if (StringUtils.isNotBlank(nomeProducto)) {
            deleteSql.append(ponerAnd ? " and": "");
            deleteSql.append(" ID_PRODUCTO = '");
            deleteSql.append(nomeProducto);
            deleteSql.append("'");
        }
        
        con.prepareStatement(deleteSql.toString()).executeUpdate();
        
    }
    
}
