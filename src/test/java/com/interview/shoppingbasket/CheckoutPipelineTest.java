package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CheckoutPipelineTest {

    CheckoutPipeline checkoutPipeline;

    @Mock
    Basket basket;

    @Mock
    CheckoutStep checkoutStep1;

    @Mock
    CheckoutStep checkoutStep2;

    @BeforeEach
    void setup() {
        checkoutPipeline = new CheckoutPipeline();
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {                              

        //normal checkout steps: consoladating the basket and getting the retailTotal
        getDummyBasket();        
        checkoutStep1 = new BasketConsolidationCheckoutStep();        
        checkoutPipeline.addStep(checkoutStep1);                

        checkoutStep2 = new RetailPriceCheckoutStep(getPricingServiceDummy());                 
        checkoutPipeline.addStep(checkoutStep2);  

        
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);
        assertEquals(paymentSummary.getRetailTotal(), 40);     

 
        //preparations for the promotions tests
        BasketItem item1 = basket.getItems().get(0);//item1
        BasketItem item2 = basket.getItems().get(1);//item2
        BasketItem item3 = basket.getItems().get(2);//item3        
        
        //getting all discounts for each type given that the retailTotal was 40
        double retailTotalOriginal = paymentSummary.getRetailTotal();
                
        assertEquals(38.5, ((RetailPriceCheckoutStep) checkoutStep2).applyPromotion(new Promotion(TypePromotion.FIFTY_PERCENT), item1, retailTotalOriginal));
        assertEquals(39.2, ((RetailPriceCheckoutStep) checkoutStep2).applyPromotion(new Promotion(TypePromotion.TEN_PERCENT), item2, retailTotalOriginal));
        assertEquals(38, ((RetailPriceCheckoutStep) checkoutStep2).applyPromotion(new Promotion(TypePromotion.TWO_FOR_ONE), item3, retailTotalOriginal));
    }

    private void getDummyBasket(){    
        basket = new Basket();
        basket.add("001", "Product 001", 1);
        basket.add("001", "Product 001", 2);
        basket.add("002", "Product 002", 4);
        basket.add("003", "Product 003", 5);        
        basket.add("004", "Product 004", 6);               
    }

    private PricingService getPricingServiceDummy(){
        
        PricingServiceImpl pricingServiceImpl = new PricingServiceImpl();
 
        pricingServiceImpl.addPricing(basket.getItems().get(0), 1D);//item 001  
        pricingServiceImpl.addPricing(basket.getItems().get(2), 2D);//item 002
        pricingServiceImpl.addPricing(basket.getItems().get(3), 1D);//item 003   
        pricingServiceImpl.addPricing(basket.getItems().get(4), 4D);//item 004   

        return pricingServiceImpl;
    }

}
