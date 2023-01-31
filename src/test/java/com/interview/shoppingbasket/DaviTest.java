package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DaviTest {
    
    @Test
    public void testConsolidate(){

        Basket basket = new Basket();
        basket.add("001", "Product 001", 10);
        basket.add("001", "Product 001", 20);
        basket.add("002", "Product 002", 30);
        basket.add("003", "Product 003", 40);
        basket.add("003", "Product 003", 50);
        basket.add("004", "Product 004", 60);

        basket.consolidateItems();
        boolean isTotalBasketOK = basket.getItems().size() == 4;
        boolean isProduct001OK = basket.getItems().get(0).getQuantity() == 30;
        boolean isProduct002OK = basket.getItems().get(1).getQuantity() == 30;
        boolean isProduct003OK = basket.getItems().get(2).getQuantity() == 90;
        boolean isProduct004OK = basket.getItems().get(3).getQuantity() == 60;
        assertTrue(isTotalBasketOK && isProduct001OK && isProduct002OK && isProduct003OK && isProduct004OK);
    }
}
