/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.vo;

import java.util.List;

/**
 *
 * ProvinciasVO
 */
public class ProvinciasVO {
    
    private List<ProvinciaVO> provincias;

    public ProvinciasVO() {
    }

    public ProvinciasVO(List<ProvinciaVO> provincias) {
        this.provincias = provincias;
    }

    public List<ProvinciaVO> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<ProvinciaVO> provincias) {
        this.provincias = provincias;
    }
    
}
