package com.exchange.api.controller;

import com.exchange.api.model.ExchangeRequest;
import com.exchange.api.model.ExchangeResponse;
import com.exchange.api.service.IExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeControllerTest {

    @InjectMocks
    private ExchangeController exchangeController;

    @Mock
    private IExchangeService exchangeService;

    private ExchangeRequest exchangeRequest;
    private ExchangeResponse exchangeResponse;

    @BeforeEach
    public void setUp() {
        exchangeRequest = new ExchangeRequest();
        exchangeRequest.setValue(BigDecimal.valueOf(10));
        exchangeRequest.setMaximizeCoins(true);

        exchangeResponse = new ExchangeResponse();
        exchangeResponse.setSuccess(true);
        exchangeResponse.setMessage("Success");
    }

    @Test
    public void testExchangeInfo() {
        when(exchangeService.exchangeInfo(exchangeRequest)).thenReturn("Exchange Info");

        ResponseEntity<String> response = exchangeController.exchangeInfo(exchangeRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Exchange Info", response.getBody());
    }

    @Test
    public void testExchange() {
        when(exchangeService.exchange(exchangeRequest)).thenReturn(exchangeResponse);

        ResponseEntity<ExchangeResponse> response = exchangeController.exchange(exchangeRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(exchangeResponse, response.getBody());
    }

    @Test
    public void testReset() {
        when(exchangeService.resetMachine()).thenReturn("Machine reset successful");

        ResponseEntity<String> response = exchangeController.reset();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Machine reset successful", response.getBody());
    }

    @Test
    public void testUpdateCoins() {
        when(exchangeService.updateCoins(25, 10)).thenReturn("Coins updated successfully");

        ResponseEntity<String> response = exchangeController.updateCoins(25, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Coins updated successfully", response.getBody());
    }
}
