package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BasketTest {
    @Test
    void emptyBasket() {
        Basket basket = new Basket();
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 0);
    }

    @Test
    void createBasketFullConstructor() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 1);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
    }

    @Test
    void createBasketWithMultipleProducts() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        basket.add("productCode2", "myProduct2", 10);
        basket.add("productCode3", "myProduct3", 10);

        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(),3);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
        assertEquals(basketSize.get(1).getProductCode(), "productCode2");
        assertEquals(basketSize.get(1).getProductName(), "myProduct2");
        assertEquals(basketSize.get(1).getQuantity(), 10);
        assertEquals(basketSize.get(2).getProductCode(), "productCode3");
        assertEquals(basketSize.get(2).getProductName(), "myProduct3");
        assertEquals(basketSize.get(2).getQuantity(), 10);
    }

    @Test
    void consolidateBasketTest() {
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
