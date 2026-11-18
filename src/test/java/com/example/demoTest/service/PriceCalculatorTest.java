package com.example.demoTest.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceCalculatorTest {

    private PriceCalculator priceCalculator=new PriceCalculator();

    @Test
    void ableToGetDiscountedPrice(){

        // ARRANGE
        double price=2000;
        double discount=50;

        // ACT
        double actualPrice=priceCalculator.calculatePrice(price,discount);

        // ASSERT
        assertEquals(1000,actualPrice);
    }

    @Test
    void checkProperExceptionThrownIfDiscountRangeExceeds(){

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                ()->priceCalculator.calculatePrice(1000,178));

        assertEquals("Discount should be within 0-100",exception.getMessage());

    }

    @Test
    void checkIfProperExceptionThrownOnInvalidPrice(){

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                ()->priceCalculator.calculatePrice(-200,90));

        assertEquals("Price can't be -ve",exception.getMessage());
    }

}
