package com.exchange.api.utils;


import com.exchange.api.model.ExchangeResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeUtilsTest {

    @Test
    public void testFormatExchangeResponse() {
        ExchangeResponse response = ExchangeResponse.builder()
                .message("Exchanged $2.5 successfully!")
                .summary("Used 5 coins of 25 cents")
                .state("Bills amount: $5.0\nCoins amount: $2.5")
                .build();

        String formattedResponse = ExchangeUtils.formatExchangeResponse(response);

        assertNotNull(formattedResponse);
        assertTrue(formattedResponse.contains("Exchanged $2.5 successfully!"));
        assertTrue(formattedResponse.contains("Statement:\nUsed 5 coins of 25 cents"));
        assertTrue(formattedResponse.contains("Bills amount: $5.0\nCoins amount: $2.5"));
    }

    @Test
    public void testFormatExchangeResponseWithNullSummary() {
        ExchangeResponse response = ExchangeResponse.builder()
                .message("Exchanged $1.0 successfully!")
                .summary(null)
                .state("Bills amount: $1.0\nCoins amount: $0.5")
                .build();

        String formattedResponse = ExchangeUtils.formatExchangeResponse(response);

        assertNotNull(formattedResponse);
        assertTrue(formattedResponse.contains("Exchanged $1.0 successfully!"));
        assertTrue(formattedResponse.contains("Statement:\nN/A"));
        assertTrue(formattedResponse.contains("Bills amount: $1.0\nCoins amount: $0.5"));
    }

    @Test
    public void testFormatExchangeResponseWithNullState() {
        ExchangeResponse response = ExchangeResponse.builder()
                .message("Exchanged $1.5 successfully!")
                .summary("Used 3 coins of 50 cents")
                .state(null)
                .build();

        String formattedResponse = ExchangeUtils.formatExchangeResponse(response);

        assertNotNull(formattedResponse);
        assertTrue(formattedResponse.contains("Exchanged $1.5 successfully!"));
        assertTrue(formattedResponse.contains("Statement:\nUsed 3 coins of 50 cents"));
        assertTrue(formattedResponse.contains("N/A"));
    }
}

