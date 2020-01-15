/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rss.elPais;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * LeerRSSElPais
 */
public class LeerRSSElPais {
    
    private final static String URL_PORTADA_EL_PAIS = "https://elpais.com/rss/elpais/portada.xml";
    
    public static void leerPortadaElPais () {
        
        try {
            
            //Url Portada
            URL url = new URL(URL_PORTADA_EL_PAIS);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
            
            RssXML xml = new RssXML();
            
            procesadorXML.setContentHandler(xml);

            InputSource arquivo = new InputSource(br);
            procesadorXML.parse(arquivo);

            for (Item item : xml.getListaItems()) {
                
                System.out.println("---------------------------------------------------------");
                System.out.println(item.getTitle());
                
                if (StringUtils.isNotBlank(item.getDescription())) {
                    System.out.println("Descripcion:");
                    System.out.println(item.getDescription());
                }
                
            }
            
        } catch (IOException | SAXException e) {
            System.err.println("Error al leer Portada.");
        }
                
    }
        
}
