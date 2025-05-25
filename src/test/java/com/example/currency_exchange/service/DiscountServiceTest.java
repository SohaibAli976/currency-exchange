// src/test/java/com/example/currency_exchange/service/DiscountServiceTest.java
package com.example.currency_exchange.service;

import com.example.currency_exchange.model.Item;
import com.example.currency_exchange.model.User;
import com.example.currency_exchange.model.UserType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {

    private final DiscountService discountService = new DiscountService();

    @Test
    void testEmployeeDiscountExcludesGroceries() {
        User user = new User("Alice", UserType.EMPLOYEE, LocalDate.now().minusYears(1));     List<Item> items = List.of(
                new Item("TV", BigDecimal.valueOf(200), "electronics"),
                new Item("Apple", BigDecimal.valueOf(100), "groceries")
        );
        BigDecimal discount = discountService.calculateDiscount(items, user);
        assertEquals(BigDecimal.valueOf(75), discount.setScale(0));
    }

    @Test
    void testAffiliateDiscount() {
        User user = new User("Bob", UserType.AFFILIATE, LocalDate.now().minusYears(3));
        List<Item> items = List.of(
                new Item("Laptop", BigDecimal.valueOf(500), "electronics")
        );
        BigDecimal discount = discountService.calculateDiscount(items, user);
        // 10% of 500 = 50, $5 per $100 = 25, total = 75
        assertEquals(BigDecimal.valueOf(75), discount.setScale(0));
    }

    @Test
    void testLoyalCustomerDiscount() {
        User user = new User("Carol", UserType.REGULAR, LocalDate.now().minusYears(3));
        List<Item> items = List.of(
                new Item("Fridge", BigDecimal.valueOf(400), "appliances")
        );
        BigDecimal discount = discountService.calculateDiscount(items, user);
        // 5% of 400 = 20, $5 per $100 = 20, total = 40
        assertEquals(BigDecimal.valueOf(40), discount.setScale(0));
    }

    @Test
    void testNoPercentageDiscountForGroceries() {
        User user = new User("Dan", UserType.EMPLOYEE, LocalDate.now().minusYears(1));
        List<Item> items = List.of(
                new Item("Banana", BigDecimal.valueOf(200), "groceries")
        );
        BigDecimal discount = discountService.calculateDiscount(items, user);
        // Only $5 per $100 applies: 10
        assertEquals(BigDecimal.valueOf(10), discount.setScale(0));
    }
}