package com.tenda.sqlLite;

import com.tenda.vo.EmpregadoVO;
import com.tenda.vo.HorasEmpregadoVO;
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
 * ListaHorasEmpregadoRepositorio
 */
public class ListaHorasEmpregadoRepositorio {
        
    /*
    Metodo para crear Objeto
    */
    private static HorasEmpregadoVO crearObjeto (final Connection con, final ResultSet rs) throws SQLException {
    
        TendaVO tenda = TendasRepositorio.buscarTendaPorNome(con, rs.getString("NOME_TENDA"));
        
        HorasEmpregadoVO horasEmpregadoVO = new HorasEmpregadoVO(rs.getInt("HORAS"), tenda);
        
        return horasEmpregadoVO;
    }
    
    /*
    Método para crear DB de Provincias
    */
    static void crearTabla (final Connection con) throws SQLException {
        
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE IF NOT EXISTS ");
        createSql.append(ConstantesDB.TABLA_EMPLEADOS_HORAS);
        createSql.append(" (NOME_TENDA VARCHAR(30), ");
        createSql.append("EMAIL_EMPREGADO VARCHAR(30), ");
        createSql.append("HORAS integer NOT NULL, ");
        createSql.append("PRIMARY KEY(NOME_TENDA, EMAIL_EMPREGADO), ");
        createSql.append("FOREIGN KEY(NOME_TENDA) REFERENCES ");
        createSql.append(ConstantesDB.TABLA_TENDAS);
        createSql.append("(NOME), ");
        createSql.append("FOREIGN KEY(EMAIL_EMPREGADO) REFERENCES ");
        createSql.append(ConstantesDB.TABLA_EMPLEADOS);
        createSql.append("(EMAIL)");
        createSql.append(");");
        
        Statement stmt = con.createStatement();
        stmt.execute(createSql.toString());
        
    }
    
    /*
    Método para buscar
    */
    public static List<HorasEmpregadoVO> listarHoras (final Connection con, final String email) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_EMPLEADOS_HORAS);
        selectSql.append(" where EMAIL_EMPREGADO = '");
        selectSql.append(email);
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        List<HorasEmpregadoVO> listaProductos = new ArrayList<>();
        
        while(rs.next()){
            //Crear Objeto
            listaProductos.add(crearObjeto(con, rs));
        }
        
        return listaProductos;
    }
    
    /*
    Método para buscar
    */
    public static HorasEmpregadoVO horasTenda (final Connection con, final String email, final String nomeTenda) throws SQLException {
    
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ");
        selectSql.append(ConstantesDB.TABLA_EMPLEADOS_HORAS);
        selectSql.append(" where EMAIL_EMPREGADO = '");
        selectSql.append(email);
        selectSql.append("' and NOME_TENDA = '");
        selectSql.append(nomeTenda);
        selectSql.append("';");
        
        Statement statement = con.createStatement();
        
        ResultSet rs = statement.executeQuery(selectSql.toString());
        
        HorasEmpregadoVO horas = null;
        
        while(rs.next()){
            //Crear Objeto
            horas = crearObjeto(con, rs);
        }
        
        return horas;
    }
    
    /*
    Método para insertar datos en la tabla
    */
    public static void insertarDatos (final Connection con, final EmpregadoVO empleado, final String nomeTenda, final Integer horas) throws SQLException {
    
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(ConstantesDB.TABLA_EMPLEADOS_HORAS);
        insertSql.append(" (NOME_TENDA, EMAIL_EMPREGADO, HORAS) ");
        insertSql.append("VALUES(?, ?, ?);");
        
        PreparedStatement pstmt = con.prepareStatement(insertSql.toString());

        //Aquí e cando engadimos o valor do nome
        pstmt.setString(1, nomeTenda);
        pstmt.setString(2, empleado.getEmail());
        pstmt.setInt(3, horas);
        
        pstmt.executeUpdate();
        
    }
    
    /*
    Método para eliminar en la tabla por nombre
    */
    public static void eliminar (final Connection con, final EmpregadoVO empregado, final TendaVO tenda) throws SQLException {
    
        Boolean crearAnd = Boolean.FALSE; 
        
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("DELETE FROM ");
        deleteSql.append(ConstantesDB.TABLA_EMPLEADOS_HORAS);
        deleteSql.append(" where ");
        if (empregado != null) {
            deleteSql.append(" EMAIL_EMPREGADO = '");
            deleteSql.append(empregado.getEmail());
            deleteSql.append("'");
            crearAnd = Boolean.TRUE; 
        }
        
        if (tenda != null) {
            deleteSql.append(crearAnd?"and":"");
            deleteSql.append(" NOME_TENDA = '");
            deleteSql.append(tenda.getNome());
            deleteSql.append("'");
        }
        deleteSql.append(";");
        
        con.prepareStatement(deleteSql.toString()).executeUpdate();
        
    }
    
}
