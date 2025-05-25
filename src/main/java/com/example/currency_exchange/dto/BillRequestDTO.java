package com.example.currency_exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillRequestDTO {
    private List<ItemDTO> items;
    private UserDTO user;
    private String originalCurrency;
    private String targetCurrency;
    
    // Getters, setters
}

