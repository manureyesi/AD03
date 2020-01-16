package com.tenda.sqlLite;

import com.tenda.utiles.UtilesProvinciasJson;
import com.tenda.vo.ProvinciaVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * CrearTablaDB
 */
public class CrearTablaDB {
    
    /*
    Método para crear las BD
    */
    public static void crearTablasDB (Connection con) throws SQLException {
    
        ProvinciasRepositorio.crearTabla(con);
        
        //Cargar datos Provincias
        List<ProvinciaVO> listaProvincias = UtilesProvinciasJson.cargarDatosProvinciasDeArchivo();
        
        //Insertar datos
        for (ProvinciaVO provinciaVO: listaProvincias) {
            ProvinciasRepositorio.insertarDatos(con, provinciaVO);
        }
        
        TendasRepositorio.crearTabla(con);
        
    }
    
}
