package com.exchange.api.service.builder;

import com.exchange.api.model.Coin;
import com.exchange.api.model.ExchangeResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeResponseBuilderTest {

    @Test
    public void testBuildSuccess() {
        List<Coin> coinsUsed = new ArrayList<>();
        coinsUsed.add(Coin.builder().currencyValue(25).quantity(100).build());
        coinsUsed.add(Coin.builder().currencyValue(10).quantity(50).build());

        List<Coin> coins = new ArrayList<>();
        coins.add(Coin.builder().currencyValue(25).quantity(100).build());
        coins.add(Coin.builder().currencyValue(10).quantity(100).build());
        coins.add(Coin.builder().currencyValue(5).quantity(100).build());
        coins.add(Coin.builder().currencyValue(1).quantity(100).build());

        List<BigDecimal> notes = new ArrayList<>();

        BigDecimal amount = BigDecimal.valueOf(30);

        ExchangeResponse response = ExchangeResponseBuilder.buildSuccess(amount, coinsUsed, coins, notes);

        assertTrue(response.isSuccess());
        assertEquals("Exchanged $30 successfully!", response.getMessage());
        assertNotNull(response.getSummary());
        assertNotNull(response.getState());
        assertNotNull(response.getTotalNotes());
        assertNotNull(response.getTotalCoins());
        assertNotNull(response.getTotal());
    }

    @Test
    public void testBuildError() {
        List<Coin> coinsUsed = new ArrayList<>();
        coinsUsed.add(Coin.builder().currencyValue(25).quantity(100).build());
        coinsUsed.add(Coin.builder().currencyValue(10).quantity(50).build());

        List<Coin> coins = new ArrayList<>();
        coins.add(Coin.builder().currencyValue(25).quantity(100).build());
        coins.add(Coin.builder().currencyValue(10).quantity(100).build());
        coins.add(Coin.builder().currencyValue(5).quantity(100).build());
        coins.add(Coin.builder().currencyValue(1).quantity(100).build());

        List<BigDecimal> notes = new ArrayList<>();

        BigDecimal amount = BigDecimal.valueOf(50);

        String message = "There are not enough coins to complete the transaction.";

        ExchangeResponse response = ExchangeResponseBuilder.buildError(message, coins, notes);

        assertFalse(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertNotNull(response.getTotalNotes());
        assertNotNull(response.getTotalCoins());
        assertNotNull(response.getTotal());
    }

    @Test
    public void testBuildExchangeInfo() {
        List<Coin> coinsUsed = new ArrayList<>();
        coinsUsed.add(Coin.builder().currencyValue(25).quantity(100).build());
        coinsUsed.add(Coin.builder().currencyValue(10).quantity(50).build());

        List<Coin> coins = new ArrayList<>();
        coins.add(Coin.builder().currencyValue(25).quantity(100).build());
        coins.add(Coin.builder().currencyValue(10).quantity(100).build());
        coins.add(Coin.builder().currencyValue(5).quantity(100).build());
        coins.add(Coin.builder().currencyValue(1).quantity(100).build());

        List<BigDecimal> notes = new ArrayList<>();

        BigDecimal amount = BigDecimal.valueOf(30);

        ExchangeResponse response = ExchangeResponseBuilder.buildSuccess(amount, coinsUsed, coins, notes);

        String exchangeInfo = ExchangeResponseBuilder.buildExchangeInfo(response);

        assertNotNull(exchangeInfo);
        assertTrue(response.isSuccess());
        assertEquals("Exchanged $30 successfully!", response.getMessage());
        assertNotNull(response.getSummary());
        assertNotNull(response.getState());
        assertNotNull(response.getTotalNotes());
        assertNotNull(response.getTotalCoins());
        assertNotNull(response.getTotal());
    }

    @Test
    public void testBuildCoinsUsedSummary() {
        List<Coin> coinsUsed = new ArrayList<>();
        coinsUsed.add(Coin.builder().currencyValue(25).quantity(100).build());
        coinsUsed.add(Coin.builder().currencyValue(10).quantity(50).build());

        StringBuilder summary = ExchangeResponseBuilder.buildCoinsUsedSummary(coinsUsed);

        assertNotNull(summary.toString());
    }

    @Test
    public void testBuildMachineStateSummary() {
        List<Coin> coins = new ArrayList<>();
        coins.add(Coin.builder().currencyValue(25).quantity(100).build());
        coins.add(Coin.builder().currencyValue(10).quantity(100).build());
        coins.add(Coin.builder().currencyValue(5).quantity(100).build());
        coins.add(Coin.builder().currencyValue(1).quantity(100).build());

        StringBuilder machineState = ExchangeResponseBuilder.buildMachineState(coins);

        assertNotNull(machineState.toString());
    }
}
