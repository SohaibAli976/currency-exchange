// src/main/java/com/example/currency_exchange/service/DiscountService.java
package com.example.currency_exchange.service;

import com.example.currency_exchange.model.Item;
import com.example.currency_exchange.model.ItemCategory;
import com.example.currency_exchange.model.User;
import com.example.currency_exchange.model.UserType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class DiscountService {
    public BigDecimal calculateDiscount(List<Item> items, User user) {
        BigDecimal total = items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal percentageDiscount = calculatePercentageDiscount(items, user);
        BigDecimal amountDiscount = BigDecimal.valueOf((total.intValue() / 100) * 5);

        return percentageDiscount.add(amountDiscount);
    }

    private BigDecimal calculatePercentageDiscount(List<Item> items, User user) {
        BigDecimal nonGroceryTotal = items.stream()
                .filter(i -> i.getCategory() != ItemCategory.GROCERIES)
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal rate = BigDecimal.ZERO;
        if (user.getUserType() == UserType.EMPLOYEE) {
            rate = BigDecimal.valueOf(0.30);
        } else if (user.getUserType() == UserType.AFFILIATE) {
            rate = BigDecimal.valueOf(0.10);
        } else if (user.getUserType() == UserType.REGULAR &&
                Period.between(user.getRegistrationDate(), LocalDate.now()).getYears() > 2) {
            rate = BigDecimal.valueOf(0.05);
        }
        return nonGroceryTotal.multiply(rate);
    }
}