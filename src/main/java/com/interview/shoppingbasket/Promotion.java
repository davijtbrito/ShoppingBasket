package com.interview.shoppingbasket;

import lombok.Data;

@Data
public class Promotion {
    
    private String productCode;
    private TypePromotion typePromotion;

    public Promotion(TypePromotion typePromotion) {        
        this.typePromotion = typePromotion;
    }

    public Promotion() {
    }        
}
