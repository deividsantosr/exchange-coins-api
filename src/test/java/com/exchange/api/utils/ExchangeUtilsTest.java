package com.exchange.api.utils;

import com.exchange.api.model.Coin;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeUtilsTest {

    @Test
    public void testGetTotalNotes() {
        List<BigDecimal> notes = Arrays.asList(
                BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(2.50)
        );
        BigDecimal totalNotes = ExchangeUtils.getTotalNotes(notes);
        assertEquals(BigDecimal.valueOf(17.50), totalNotes);
    }

    @Test
    public void testGetTotalCoins() {
        List<Coin> coins = Arrays.asList(
                Coin.builder().currencyValue(25).quantity(100).build(),
                Coin.builder().currencyValue(10).quantity(200).build(),
                Coin.builder().currencyValue(5).quantity(150).build()
        );
        BigDecimal totalCoins = ExchangeUtils.getTotalCoins(coins);
        assertEquals(BigDecimal.valueOf(52.50), totalCoins);
    }

    @Test
    public void testGetTotal() {
        BigDecimal coinTotal = BigDecimal.valueOf(52.50);
        BigDecimal notesTotal = BigDecimal.valueOf(17.50);
        BigDecimal total = ExchangeUtils.getTotal(coinTotal, notesTotal);
        assertEquals(BigDecimal.valueOf(70.00), total);
    }
}
