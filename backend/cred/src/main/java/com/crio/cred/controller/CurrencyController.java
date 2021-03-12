package com.crio.cred.controller;

import com.crio.cred.dto.CurrencyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
@CrossOrigin
@RequestMapping("/currency")
public class CurrencyController {
    @GetMapping
    public List<CurrencyDTO> getCurrencies() {
        return Currency.getAvailableCurrencies()
                .stream()
                .map(currency -> new CurrencyDTO(currency.getCurrencyCode(),
                        currency.getSymbol(),
                        currency.getDisplayName()))
                .sorted()
                .collect(Collectors.toList());
    }
}
