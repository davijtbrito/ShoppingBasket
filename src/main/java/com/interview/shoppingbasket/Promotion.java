package com.interview.shoppingbasket;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Promotion {
    
    private String codeItem;
    private TypePromotion typePromotion;
}
