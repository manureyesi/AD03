package com.tenda.sqlLite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * DBUtiles
 */
public class DBUtiles {
    
     /*
    Este método crea unha nova base de datos en SQLLite co nome que se lle pasa como argunmento
    */
    private static Connection crearDB (String filename){
        
        String databaseFile = "jdbc:sqlite:" + filename;
        
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(databaseFile);
            if(connection != null){
                CrearTablaDB.crearTablasDB(connection);
            }
        }
        catch(SQLException e){
            System.err.println("Error al crear DB");
        }
        return connection;
    }
    
    /*
    Esta clase conéctase a base de datos SQLLite que se lle pasa o nome da base de datos
    */
    public static Connection conectarseDB (String filename){
        
        Connection connection = null;
        File file = new File(filename);        
        if (!file.exists()) {
            connection = crearDB(filename);
        } else {
        
        
            try {
                //Creamos a conexión a base de datos
                connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
            } catch(SQLException e){
                System.err.println("Error de conexion DB");
            }
        }
        return connection;
    }
    
    /*
    Este método desconectase dunha base de datos en SQLLite a que se lle pasa a conexión
    */
    public static void desconectarseDB (Connection connection){
        
        try {
            if(connection != null){
                connection.close();
            }
        } catch(SQLException e){
            System.err.println("Error al cerrar conexion DB.");
        }
    }
    
}
