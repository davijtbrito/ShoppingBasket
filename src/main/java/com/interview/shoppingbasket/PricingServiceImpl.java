package com.interview.shoppingbasket;

import java.util.Map;
import java.util.Optional;

public class PricingServiceImpl implements PricingService{
    
    private Map<BasketItem, Double> pricingList;        
    private PromotionsService promotionsService;    

    @Override
    public double getPrice(String itemCode) {
        
        TypePromotion typePromotion = ((PromotionsServiceImpl) promotionsService).getTypePromotion(itemCode);
        double price = 0;
        Optional<BasketItem> item = this.pricingList.keySet().stream().filter(p -> p.getProductCode().equals(itemCode)).findFirst();
        double listPrice = this.pricingList.get(item.get());

        switch (typePromotion) {
            case FIFTY_PERCENT:                    
                price = listPrice* 0.5;                                    
                break;

            case TEN_PERCENT:
                price = listPrice * 0.9;                        
                break;

            case TWO_FOR_ONE:
                
                if (item.get().getQuantity() % 2 == 1) {
                    price = (((item.get().getQuantity() - 1) / 2) + listPrice) / item.get().getQuantity();                    

                }else{
                    price = this.pricingList.get(item.get()) / 2;          
                }      
                
                break;
        
            default:
                break;
        }

        return price;
    }     
    
    public void addPricing(Promotion promotion, BasketItem item, double price){
        
        this.pricingList.put(item, price);
        ((PromotionsServiceImpl) promotionsService).addPromotion(promotion);        
    }
}
