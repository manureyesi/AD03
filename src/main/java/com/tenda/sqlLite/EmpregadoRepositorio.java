package com.tenda.sqlLite;

import com.tenda.vo.EmpregadoVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * EmpregadoRepositorio
 */
public class EmpregadoRepositorio {
        
    /*
    Metodo para crear Objeto
    */
    private static EmpregadoVO crearObjeto (final Connection con, final ResultSet rs) throws SQLException {
    
        EmpregadoVO empregado = new EmpregadoVO(
                rs.getString("NOME"),
                rs.getString("APELLIDOS"),
                rs.getString("EMAIL"));
        
        empregado.setListaHoras(ListaHorasEmpregadoRepositorio.listarHoras(con, rs.getString("EMAIL")));
        
        return empregado;
    }
    
    /*
    Método para crear DB de Provincias
    */
    static void crearTabla (final Connection con) throws SQLException {
        
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE IF NOT EXISTS ");
        createSql.append(ConstantesDB.TABLA_EMPLEADOS);
        createSql.append(" (NOME VARCHAR(30), ");
        createSql.append("APELLIDOS VARCHAR(100), ");
        createSql.append("EMAIL VARCHAR(30), ");
        createSql.append("PRIMARY KEY(EMAIL)");
        createSql.append(");");
        
        Statement stmt = con.createStatement();
        stmt.execute(createSql.toString());
        
    }
    
    /*
    Método para insertar datos en la tabla
    */
    public static void insertarDatos (final Connection con, final EmpregadoVO empregado) throws SQLException {
    
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(ConstantesDB.TABLA_EMPLEADOS);
        insertSql.append(" (NOME, APELLIDOS, EMAIL) ");
        insertSql.append("VALUES(?, ?, ?);");
        
        PreparedStatement pstmt = con.prepareStatement(insertSql.toString());

        //Aquí e cando engadimos o valor do nome
        pstmt.setString(1, empregado.getNome());
        pstmt.setString(2, empregado.getApelidos());
        pstmt.setString(3, empregado.getEmail());
        
        pstmt.executeUpdate();
        
    }
    
    /*
    Método para buscar en la tabla los productos
    */
    public static List<EmpregadoVO> listarEmpregado (final Connection con) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_EMPLEADOS);
        selectSql.append(" where 1 = 1;");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        List<EmpregadoVO> listaProductos = new ArrayList<>();
        
        while(rs.next()){
            //Crear Objeto
            listaProductos.add(crearObjeto(con, rs));
        }
        
        return listaProductos;
    }
    
    /*
    Método para buscar en la tabla los productos
    */
    public static EmpregadoVO buscar (final Connection con, final String email) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_EMPLEADOS);
        selectSql.append(" where EMAIL = '");
        selectSql.append(email);
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        EmpregadoVO empregado = null;
        
        while(rs.next()){
            //Crear Objeto
            empregado = crearObjeto(con, rs);
        }
        
        return empregado;
    }
    
    /*
    Método para eliminar en la tabla por nombre
    */
    public static void eliminar (final Connection con, final EmpregadoVO empregado) throws SQLException {
    
        ListaHorasEmpregadoRepositorio.eliminar(con, empregado, null);
        
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("DELETE FROM ");
        deleteSql.append(ConstantesDB.TABLA_EMPLEADOS);
        deleteSql.append(" where ");
        deleteSql.append(" EMAIL = '");
        deleteSql.append(empregado.getEmail());
        deleteSql.append("';");
        
        con.prepareStatement(deleteSql.toString()).executeUpdate();
        
    }
    
}
