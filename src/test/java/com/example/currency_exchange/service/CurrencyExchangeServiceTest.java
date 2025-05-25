// src/test/java/com/example/currency_exchange/service/CurrencyExchangeServiceTest.java
package com.example.currency_exchange.service;

import com.example.currency_exchange.dto.ExchangeRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CurrencyExchangeServiceTest {

    private RestTemplate restTemplate;
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    void setUp() throws Exception {
        restTemplate = Mockito.mock(RestTemplate.class);
        currencyExchangeService = new CurrencyExchangeService(restTemplate);

        // Set private fields via reflection
        Field apiUrlField = CurrencyExchangeService.class.getDeclaredField("apiUrl");
        apiUrlField.setAccessible(true);
        apiUrlField.set(currencyExchangeService, "https://fake.api/{base_currency}");

        Field apiKeyField = CurrencyExchangeService.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(currencyExchangeService, "dummy-key");
    }

    @Test
    void testGetExchangeRate() {
        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setRates(Map.of("EUR", BigDecimal.valueOf(0.9)));
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateResponse.class)))
                .thenReturn(mockResponse);

        BigDecimal rate = currencyExchangeService.getExchangeRate("USD", "EUR");
        assertEquals(BigDecimal.valueOf(0.9), rate);
    }

    @Test
    void testConvertCurrency() {
        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setRates(Map.of("EUR", BigDecimal.valueOf(0.8)));
        when(restTemplate.getForObject(anyString(), eq(ExchangeRateResponse.class)))
                .thenReturn(mockResponse);

        BigDecimal result = currencyExchangeService.convertCurrency(BigDecimal.valueOf(100), "USD", "EUR");
        assertEquals(BigDecimal.valueOf(80.0), result);
    }
}