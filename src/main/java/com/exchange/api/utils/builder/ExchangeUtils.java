package com.exchange.api.utils.builder;

import com.exchange.api.model.Coin;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeUtils {
    public static final int INITIAL_COIN_QUANTITY = 100;

    public static BigDecimal getTotalNotes(List<BigDecimal> notes) {
        return notes.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal getTotalCoins(List<Coin> coins) {
        return coins.stream()
                .map(coin -> BigDecimal.valueOf(coin.getQuantity() * coin.getCurrencyValue() / 100.0))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal getTotal(BigDecimal coinTotal, BigDecimal notesTotal) {
        return coinTotal.add(notesTotal);
    }
}
