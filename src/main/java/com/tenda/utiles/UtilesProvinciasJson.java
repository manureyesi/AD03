/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.utiles;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tenda.vo.ProvinciaVO;
import com.tenda.vo.ProvinciasVO;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * UtilesProvinciasJson
 */
public class UtilesProvinciasJson {
    
    public final static String NOME_ARQUIVO_JSON = "provincias.json";
    
    public static List<ProvinciaVO> cargarDatosProvinciasDeArchivo () {
        
        ProvinciasVO provinciasVO = new ProvinciasVO();
        File arquivoProvincias = new File(NOME_ARQUIVO_JSON);
        
        //Comprobar si existe
        if (arquivoProvincias.exists()) {
        
            try {
                Gson gson = new Gson();
                provinciasVO = gson.fromJson(
                        new FileReader(arquivoProvincias),
                        ProvinciasVO.class);
                
            } catch (JsonSyntaxException | IOException e) {
                System.err.println("Error al cargar fichero.");
            }
            
        }
        
        return provinciasVO.getProvincias();
    }
    
}
