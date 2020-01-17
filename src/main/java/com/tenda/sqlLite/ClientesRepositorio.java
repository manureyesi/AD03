package com.tenda.sqlLite;

import com.tenda.vo.ClienteVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * ClientesRepositorio
 */
public class ClientesRepositorio {
        
    /*
    Metodo para crear Objeto
    */
    private static ClienteVO crearObjeto (final ResultSet rs) throws SQLException {
    
        ClienteVO cliente = new ClienteVO();
        cliente.setNome(rs.getString("NOME"));
        cliente.setApelidos(rs.getString("APELLIDOS"));
        cliente.setEmail(rs.getString("EMAIL"));
        return cliente;
    }
    
    /*
    Método para crear DB de Provincias
    */
    static void crearTabla (final Connection con) throws SQLException {
        
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE IF NOT EXISTS ");
        createSql.append(ConstantesDB.TABLA_CLIENTES);
        createSql.append(" (NOME VARCHAR(30) PRIMARY KEY, ");
        createSql.append("APELLIDOS VARCHAR(100) PRIMARY KEY, ");
        createSql.append("EMAIL VARCHAR(30) NOT NULL);");
        
        Statement stmt = con.createStatement();
        stmt.execute(createSql.toString());
        
    }
    
    /*
    Método para insertar datos en la tabla
    */
    public static void insertarDatos (final Connection con, final ClienteVO cliente) throws SQLException {
    
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(ConstantesDB.TABLA_CLIENTES);
        insertSql.append(" (NOME, APELLIDOS, EMAIL) ");
        insertSql.append("VALUES(?, ?, ?);");
        
        PreparedStatement pstmt = con.prepareStatement(insertSql.toString());

        //Aquí e cando engadimos o valor do nome
        pstmt.setString(1, cliente.getNome());
        pstmt.setString(2, cliente.getApelidos());
        pstmt.setString(3, cliente.getEmail());
        
        pstmt.executeUpdate();
        
    }
    
    /*
    Método para buscar en la tabla por nombre
    */
    public static List<ClienteVO> listarClientes (final Connection con) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_CLIENTES);
        selectSql.append(" where 1 = 1;");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        List<ClienteVO> listaClientes = new ArrayList<>();
        
        while(rs.next()){
            //Crear Objeto
            listaClientes.add(crearObjeto(rs));
        }
        
        return listaClientes;
    }
    
    /*
    Método para buscar en la tabla por nombre
    */
    public static ClienteVO buscarPorNombre (final Connection con, final ClienteVO cliente) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_CLIENTES);
        selectSql.append(" where NOME = '");
        selectSql.append(cliente.getNome());
        selectSql.append("' and APELLIDOS = '");
        selectSql.append(cliente.getApelidos());
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        ClienteVO clienteAux = null;
        
        while(rs.next()){
            //Crear Objeto
            clienteAux = crearObjeto(rs);
        }
        
        return clienteAux;
    }
    
}
