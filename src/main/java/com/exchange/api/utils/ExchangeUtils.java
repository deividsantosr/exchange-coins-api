package com.exchange.api.utils;

import com.exchange.api.model.ExchangeResponse;

public class ExchangeUtils {
    public static final int INITIAL_COIN_QUANTITY = 100;

    public static String formatExchangeResponse(ExchangeResponse exchangeResponse) {
        return exchangeResponse.getMessage()
                .concat("\n")
                .concat("\nStatement:\n")
                .concat(exchangeResponse.getSummary() != null ? exchangeResponse.getSummary() : "N/A")
                .concat("\n")
                .concat(exchangeResponse.getState() != null ? exchangeResponse.getState() : "N/A");
    }
}
