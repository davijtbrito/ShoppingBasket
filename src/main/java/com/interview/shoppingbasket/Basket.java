package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        System.out.println("Before Consolidate: " + this.items.size());                
        
        this.items = removeDuplicates();                

        System.out.println("After Consolidate: " + this.items.size());
    }    

    private List<BasketItem> removeDuplicates() {
        Map<String, BasketItem> map = new HashMap<>();
        for (BasketItem basketItem : this.items) {
          if (map.containsKey(basketItem.getProductCode())) {
            BasketItem existingBasketItem = map.get(basketItem.getProductCode());
            existingBasketItem.setQuantity(existingBasketItem.getQuantity() + basketItem.getQuantity());
          } else {
            map.put(basketItem.getProductCode(), basketItem);
          }
        }
    
        return new ArrayList<>(map.values());
      }
}
