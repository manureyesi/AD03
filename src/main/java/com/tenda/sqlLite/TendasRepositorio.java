package com.tenda.sqlLite;

import com.tenda.vo.ClienteVO;
import com.tenda.vo.ProvinciaVO;
import com.tenda.vo.TendaVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * TendasRepositorio
 */
public class TendasRepositorio {
    
    /*
    Metodo para crear Objeto
    */
    private static TendaVO crearObjeto (final ResultSet rs, final Connection connection) throws SQLException {
    
        ProvinciaVO provincia = null;
        if (rs.getString("ID_PROVINCIA") != null) {
            provincia = ProvinciasRepositorio.buscarProvinciaPorId(
                    connection, 
                    rs.getInt("ID_PROVINCIA"));
        }
        
        TendaVO tenda = new TendaVO(
                rs.getString("NOME"),
                rs.getString("CIDADE"),
                provincia
        );
                
        return tenda;
    }
    
    /*
    Método para crear DB de Provincias
    */
    public static void crearTabla (final Connection con) throws SQLException {
        
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE IF NOT EXISTS ");
        createSql.append(ConstantesDB.TABLA_TENDAS);
        createSql.append(" (NOME VARCHAR(30) PRIMARY KEY, ");
        createSql.append("ID_PROVINCIA integer NOT NULL, ");
        createSql.append("CIDADE VARCHAR(30) NOT NULL, ");
        createSql.append("FOREIGN KEY(ID_PROVINCIA) REFERENCES ");
        createSql.append(ConstantesDB.TABLA_PROVINCIAS);
        createSql.append("(ID));");
        
        Statement stmt = con.createStatement();
        stmt.execute(createSql.toString());
        
    }
    
    /*
    Método para insertar datos en la tabla
    */
    public static void insertarDatos (final Connection con, final TendaVO tenda) throws SQLException {
    
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(ConstantesDB.TABLA_TENDAS);
        insertSql.append(" ( ");
        insertSql.append("NOME, CIDADE, ID_PROVINCIA");
        insertSql.append(" ) ");
        insertSql.append("VALUES(?, ?");
        insertSql.append(tenda.getProvincia() != null ? ",?" : "");
        insertSql.append(");");
        
        PreparedStatement pstmt = con.prepareStatement(insertSql.toString());

        //Aquí e cando engadimos o valor do nome
        pstmt.setString(1, tenda.getNome());
        pstmt.setString(2, tenda.getCidade());
        pstmt.setInt(3, tenda.getProvincia().getId());
        pstmt.executeUpdate();
        
    }
    
    /*
    Método para buscar en la tabla por id
    */
    public static TendaVO buscarTendaPorNome (final Connection con, final String nomeTenda) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_TENDAS);
        selectSql.append(" where nome = '");
        selectSql.append(nomeTenda);
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        TendaVO tenda = null;
        
        while(rs.next()){
            //Crear Objeto
            tenda = crearObjeto(rs, con);
        }
        
        return tenda;
    }
    
    /*
    Método para buscar en la tabla por id
    */
    public static List<TendaVO> buscarTendas (final Connection con) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_TENDAS);
        selectSql.append(" where 1 = 1;");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        List<TendaVO> listaTendas = new ArrayList<>();
        
        while(rs.next()){
            //Crear Objeto
            listaTendas.add(crearObjeto(rs, con));
        }
        
        return listaTendas;
    }
    
    /*
    Método para eliminar en la tabla por nombre
    */
    public static void eliminar (final Connection con, final String nomeTenda) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("DELETE * FROM ");
        selectSql.append(ConstantesDB.TABLA_TENDAS);
        selectSql.append(" where NOME = '");
        selectSql.append(nomeTenda);
        selectSql.append("';");
        
        con.createStatement();
        
    }
    
}
