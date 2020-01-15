/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rss.elPais;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * RssXML
 */
public class RssXML extends DefaultHandler {
    
    private List<Item> listaItems;
    
    private Item itemAux;
    
    private String cadeaTexto;
    
    public RssXML () {
        super();
    }

    public List<Item> getListaItems() {
        return listaItems;
    }
    
    /*
    Este metodo executase ao comezar a ler unha etiqueta
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    
        if ("channel".equals(localName)) {
            this.listaItems = new ArrayList<>();
        } else if ("item".equals(localName)) {
            this.itemAux = new Item();
        }
        
    }
    
    /*
    Este método executase cando se finaliza de ler unha etiqueta
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (this.itemAux != null && "title".equals(localName)) {
            this.itemAux.setTitle(cadeaTexto);
        } else if (this.itemAux != null && "description".equals(localName)) {
            this.itemAux.setDescription(cadeaTexto);
        } else if ("item".equals(localName)) {
            this.listaItems.add(this.itemAux);
            this.itemAux = new Item();
        }
    }
    
    /*
    Este metodo executase cando se le unha cadea de texto
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //Gardamos todo o texto entre caracteres na cadea de texto auxiliar
        this.cadeaTexto = new String(ch,start,length);
    }
    
}
