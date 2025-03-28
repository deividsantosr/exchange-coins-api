package com.exchange.api.service;


import com.exchange.api.model.Coin;
import com.exchange.api.model.ExchangeRequest;
import com.exchange.api.model.ExchangeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeServiceTest {

    @InjectMocks
    private ExchangeService exchangeService;

    @Mock
    private Coin coinMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExchangeInfo() {
        ExchangeRequest request = new ExchangeRequest();
        request.setValue(BigDecimal.valueOf(30));
        request.setMaximizeCoins(true);

        String result = exchangeService.exchangeInfo(request);

        assertNotNull(result);
        assertTrue(result.contains("Exchanged $30 successfully!"));
    }

    @Test
    public void testExchangeSuccess() {
        ExchangeRequest request = new ExchangeRequest();
        request.setValue(BigDecimal.valueOf(30));
        request.setMaximizeCoins(true);

        ExchangeResponse response = exchangeService.exchange(request);

        assertTrue(response.isSuccess());
        assertEquals("Exchanged $30 successfully!", response.getMessage());
        assertNotNull(response.getUsedCoins());
        assertTrue(response.getUsedCoins().size() > 0);
    }

    @Test
    public void testExchangeInsufficientCoins() {
        ExchangeRequest request = new ExchangeRequest();
        request.setValue(BigDecimal.valueOf(1000));
        request.setMaximizeCoins(false);

        ExchangeResponse response = exchangeService.exchange(request);

        assertFalse(response.isSuccess());
        assertEquals("There are not enough coins to complete the transaction.", response.getMessage());
    }

    @Test
    public void testUpdateCoins() {
        int coinValue = 25;
        int quantityToAdd = 10;

        String result = exchangeService.updateCoins(coinValue, quantityToAdd);

        assertEquals("Coin updated successfully!", result);
    }

    @Test
    public void testResetMachine() {
        String result = exchangeService.resetMachine();

        assertEquals("Machine reset successfully!", result);
    }

    @Test
    public void testMachineStatusAfterExchange() {
        ExchangeRequest request = new ExchangeRequest();
        request.setValue(BigDecimal.valueOf(30));
        request.setMaximizeCoins(true);

        ExchangeResponse exchangeResponse = exchangeService.exchange(request);

        assertTrue(exchangeResponse.getCurrentCoins().stream().anyMatch(coin -> coin.getQuantity() < 10));
    }

    @Test
    public void testSortCoinsMaximize() {
        ExchangeRequest request = new ExchangeRequest();
        request.setValue(BigDecimal.valueOf(30));
        request.setMaximizeCoins(true);

        ExchangeResponse exchangeResponse = exchangeService.exchange(request);

        List<Coin> coins = exchangeResponse.getCurrentCoins();
        assertTrue(coins.get(0).getCurrencyValue() < coins.get(1).getCurrencyValue());
    }

    @Test
    public void testSortCoinsMinimize() {
        ExchangeRequest request = new ExchangeRequest();
        request.setValue(BigDecimal.valueOf(1));
        request.setMaximizeCoins(false);

        ExchangeResponse exchangeResponse = exchangeService.exchange(request);

        List<Coin> coins = exchangeResponse.getCurrentCoins();
        assertTrue(coins.get(0).getCurrencyValue() > coins.get(1).getCurrencyValue());
    }
}
