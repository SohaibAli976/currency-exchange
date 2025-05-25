// src/main/java/com/example/currency_exchange/service/CurrencyExchangeService.java
package com.example.currency_exchange.service;

import com.example.currency_exchange.dto.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CurrencyExchangeService {
    @Value("${exchange.api.key}")
    private String apiKey;

    @Value("${exchange.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        String url = apiUrl.replace("{base_currency}", baseCurrency) + "?apikey=" + apiKey;
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response != null && response.getRates() != null) {
            return response.getRates().get(targetCurrency);
        }
        throw new RuntimeException("Failed to get exchange rate");
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        BigDecimal rate = getExchangeRate(fromCurrency, toCurrency);
        return amount.multiply(rate);
    }
}