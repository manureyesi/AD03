/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.utiles;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tenda.vo.ProgramaVO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * UtilesTendaJson
 */
public class UtilesTendaJson {
    
    public final static String NOME_ARQUIVO_JSON = "tenda.json";
    
    public static void gardarBackUpArchivo (final ProgramaVO datosPrograma, final String nome) {
    
        StringBuilder nombreArchivo = new StringBuilder();
        
        nombreArchivo.append(nome);
                
        Gson gson = new Gson();
        String datosProgramaJson = gson.toJson(datosPrograma);  

        nombreArchivo.append(".backup");

        File arquivoBackup = new File(nombreArchivo.toString());

        try {

            FileOutputStream fileOut = new FileOutputStream(arquivoBackup);
            DataOutputStream fluxoDatos = new DataOutputStream(fileOut);

            fluxoDatos.writeBytes(datosProgramaJson);

            fluxoDatos.close();

        } catch (IOException e) {
            System.err.println("Error ao gardar a copia de seguridade.");
        }
        
    }
    
}
