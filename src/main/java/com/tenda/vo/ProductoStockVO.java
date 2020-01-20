/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenda.vo;

/**
 *
 * ProductoStockVO
 */
public class ProductoStockVO extends ProductoVO {
    
    private Integer stock;

    public ProductoStockVO(Integer stock, ProductoVO producto) {
        super(producto);
        this.stock = stock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
