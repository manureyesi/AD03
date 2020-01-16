package com.tenda.sqlLite;

import com.tenda.vo.ProvinciaVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * ProvinciasRepositorio
 */
public class ProvinciasRepositorio {
        
    /*
    Metodo para crear Objeto
    */
    private static ProvinciaVO crearObjeto (final ResultSet rs) throws SQLException {
    
        ProvinciaVO provincia = new ProvinciaVO();
        provincia.setId(rs.getInt("ID"));
        provincia.setNome(rs.getString("NOME"));
        
        return provincia;
    }
    
    /*
    Método para crear DB de Provincias
    */
    public static void crearTabla (final Connection con) throws SQLException {
        
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE IF NOT EXISTS ");
        createSql.append(ConstantesDB.TABLA_PROVINCIAS);
        createSql.append(" (ID integer PRIMARY KEY, ");
        createSql.append("NOME text NOT NULL);");
        
        Statement stmt = con.createStatement();
        stmt.execute(createSql.toString());
        
    }
    
    /*
    Método para insertar datos en la tabla
    */
    public static void insertarDatos (final Connection con, final ProvinciaVO provincia) throws SQLException {
    
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(ConstantesDB.TABLA_PROVINCIAS);
        insertSql.append(" ( ");
        insertSql.append("ID, NOME");
        insertSql.append(" ) ");
        insertSql.append("VALUES(?, ?);");
        
        PreparedStatement pstmt = con.prepareStatement(insertSql.toString());

        //Aquí e cando engadimos o valor do nome
        pstmt.setInt(1, provincia.getId());
        pstmt.setString(2, provincia.getNome());
        pstmt.executeUpdate();
        
    }
    
    /*
    Método para buscar en la tabla por id
    */
    public static ProvinciaVO buscarProvinciaPorId (final Connection con, final Integer idProvincia) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_PROVINCIAS);
        selectSql.append(" where id = ");
        selectSql.append(idProvincia);
        selectSql.append(";");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        ProvinciaVO provincia = null;
        
        while(rs.next()){
            //Crear Objeto
            provincia = crearObjeto(rs);
        }
        
        return provincia;
    }
    
}
