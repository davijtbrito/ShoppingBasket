package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PromotionsServiceImpl implements PromotionsService{

    private List<Promotion> promotions;    

    public PromotionsServiceImpl() {

        this.promotions = new ArrayList<>();
    }

    @Override
    public List<Promotion> getPromotions(Basket basket) {
        
        List<Promotion> listPromotions = new ArrayList<>();
        for (BasketItem item : basket.getItems()){
            
            Optional<Promotion> optProm = this.promotions.stream().filter(p -> p.getProductCode().equals(item.getProductCode())).findFirst();
            if (optProm.isPresent()){
                listPromotions.add(optProm.get());
            }            
        }

        return listPromotions;        
    }    

    public void addPromotion(Promotion promotion){
        Optional<Promotion> optProm = this.promotions.stream().filter(p -> p.getProductCode().equals(promotion.getProductCode())).findAny();
        if (optProm.isPresent()){
            this.promotions.remove(optProm.get());            
        }   

        this.promotions.add(promotion);   
    }

    public TypePromotion getTypePromotion(String itemCode){
        Optional<Promotion> type = this.promotions.stream().filter(p -> p.getProductCode().equals(itemCode)).findFirst();
        if (type.isPresent()){
            return type.get().getTypePromotion();
        }

        return TypePromotion.NONE;
    }
}
