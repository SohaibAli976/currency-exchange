package com.example.currency_exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillResponseDTO {
    private BigDecimal originalAmount;
    private BigDecimal discountedAmount;
    private BigDecimal finalAmount;
    private String targetCurrency;
    
    // Getters, setters
}
