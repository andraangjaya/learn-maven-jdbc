package com.flowza.retail.product;

import com.flowza.retail.exception.InvalidDataException;

import java.math.BigDecimal;

public class ProductValidator {
    public static void validator(Product product) {

        if(product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.product.Product Name");
        }

        if(product.getSellPrice() == null || product.getSellPrice().compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidDataException("com.flowza.retail.product.Product Sell Price");
        }

        if(product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.product.Product Category");
        }

        if(product.getStatus() == null){
            throw new InvalidDataException("com.flowza.retail.product.Product Status");
        }

        if(product.getStock() == null || product.getStock() < 0){
            throw new InvalidDataException("com.flowza.retail.product.Product Stock");
        }

        if(product.getBarcode() == null || product.getBarcode().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.product.Product Barcode");
        }

        if(product.getSubCategory() == null || product.getSubCategory().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.product.Product Sub Category");
        }

        if(product.getPurchasePrice() == null || product.getPurchasePrice().compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidDataException("com.flowza.retail.product.Product Purchase Price");
        }
    }
}
