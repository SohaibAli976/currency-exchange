package com.example.currency_exchange.controller;

import com.example.currency_exchange.dto.BillRequestDTO;
import com.example.currency_exchange.dto.BillResponseDTO;
import com.example.currency_exchange.dto.ItemDTO;
import com.example.currency_exchange.dto.UserDTO;
import com.example.currency_exchange.model.Item;
import com.example.currency_exchange.model.ItemCategory;
import com.example.currency_exchange.model.User;
import com.example.currency_exchange.model.UserType;
import com.example.currency_exchange.service.CurrencyExchangeService;
import com.example.currency_exchange.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BillController {
    private final DiscountService discountService;
    private final CurrencyExchangeService currencyExchangeService;
    
    public BillController(DiscountService discountService, CurrencyExchangeService currencyExchangeService) {
        this.discountService = discountService;
        this.currencyExchangeService = currencyExchangeService;
    }
    
    @PostMapping("/calculate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BillResponseDTO> calculateBill(@RequestBody BillRequestDTO request) {
        // Convert DTOs to domain objects
        List<Item> items = convertToItems(request.getItems());
        User user = convertToUser(request.getUser());
        
        // Calculate original amount
        BigDecimal originalAmount = items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Apply discounts
        BigDecimal discount = discountService.calculateDiscount(items, user);
        BigDecimal discountedAmount = originalAmount.subtract(discount);
        
        // Convert currency
        BigDecimal finalAmount = currencyExchangeService.convertCurrency(
                discountedAmount, 
                request.getOriginalCurrency(), 
                request.getTargetCurrency()
        );
        
        // Prepare response
        BillResponseDTO response = new BillResponseDTO();
        response.setOriginalAmount(originalAmount);
        response.setDiscountedAmount(discountedAmount);
        response.setFinalAmount(finalAmount);
        response.setTargetCurrency(request.getTargetCurrency());
        
        return ResponseEntity.ok(response);
    }
    
    // Helper methods to convert DTOs
    private List<Item> convertToItems(List<ItemDTO> itemDTOs) {
        return itemDTOs.stream().map(dto -> {
            Item item = new Item();
            item.setName(dto.getName());
            item.setPrice(dto.getPrice());
            item.setCategory(
                    dto.getCategory() != null
                            ? ItemCategory.valueOf(dto.getCategory().toUpperCase())
                            : null
            );
            return item;
        }).toList();
    }

    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUserType(
                userDTO.getUserType() != null
                        ? UserType.valueOf(userDTO.getUserType().toUpperCase())
                        : null
        );
        user.setRegistrationDate(userDTO.getRegistrationDate());
        return user;
    }}