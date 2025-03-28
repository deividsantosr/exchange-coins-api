package com.exchange.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponse {
    private boolean success;
    private String message;
    private String summary;
    private List<Coin> usedCoins;
    private List<Coin> currentCoins;
    private List<BigDecimal> currentNotes;
    private BigDecimal totalCoins;
    private BigDecimal totalNotes;
    private BigDecimal total;
    private String state;
}