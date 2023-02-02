package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

public class PricingServiceImpl implements PricingService{        
    
    private PromotionsService promotionsService;        

    private List<BasketItem> listPricings;

    public PricingServiceImpl() {

        this.listPricings = new ArrayList<>();    
        this.promotionsService = new PromotionsServiceImpl();            
    }

    @Override
    public double getPrice(String itemCode) {
        
        double price = 0;
        
        TypePromotion typePromotion = ((PromotionsServiceImpl) promotionsService).getTypePromotion(itemCode);        
        Optional<BasketItem> item = this.listPricings.stream().filter(p -> p.getProductCode().equals(itemCode)).findFirst();

        if (item.isPresent()){

            double listPrice = item.get().getProductRetailPrice();

            switch (typePromotion) {

                case NONE:                    
                    price = listPrice; 
                    break;

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
                        price = listPrice * 0.5;          
                    }      
                    
                    break;
            
                default:
                    break;
            }


        }
        
        return price;
    }     
    
    /**
     * Adding prices for the item in the basket.
     * @param item
     * @param price
     */
    public void addPricing(BasketItem item, double price){

        listPricings.remove(item);    

        item.setProductRetailPrice(price);        
        listPricings.add(item);                         
    }    

    /**
     * The method will add the promotion for the product and return the 
     * retailTotal based in the promotion given.
     * 
     * @param prom the promotion that will be applied
     * @param item the item in the basket
     * @param retailTotal the retailTotal in the PaymentSummary
     * @return return the new retailTotal calculated after the promotion
     */
    public double addPromotion(Promotion prom, BasketItem item, double retailTotal){ 
        
        Optional<BasketItem> itemList = listPricings.stream().filter(p -> p.getProductCode().equals(item.getProductCode())).findAny();

        retailTotal -= (itemList.get().getProductRetailPrice() * itemList.get().getQuantity());

        itemList.get().setProductRetailPrice(item.getProductRetailPrice());        

        prom.setProductCode(item.getProductCode());
        ((PromotionsServiceImpl) this.promotionsService).addPromotion(prom);

        return retailTotal + (getPrice(itemList.get().getProductCode()) * itemList.get().getQuantity());                
    }
}
