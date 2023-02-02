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
        // Exercise - implement testing passing through all checkout steps

        Basket basket = getDummyBasket();        

        Promotion p = new Promotion("Product 003", TypePromotion.TWO_FOR_ONE);
        PricingServiceImpl pricingServiceImpl = new PricingServiceImpl();
        pricingServiceImpl.addPricing(p, basket.getItems().get(2), 1D); 

        RetailPriceCheckoutStep priceCheckoutStep = new RetailPriceCheckoutStep(pricingServiceImpl);
        priceCheckoutStep.applyPromotion(p, basket.getItems().get(2), 0);
    }

    private Basket getDummyBasket(){    
        Basket basket = new Basket();
        basket.add("001", "Product 001", 1);
        basket.add("001", "Product 001", 2);
        basket.add("002", "Product 002", 4);
        basket.add("003", "Product 003", 5);        
        basket.add("004", "Product 004", 6);
        basket.consolidateItems();

        return basket;
    }

}
