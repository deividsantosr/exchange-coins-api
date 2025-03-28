package com.exchange.api.service;

import com.exchange.api.model.ExchangeRequest;
import com.exchange.api.model.ExchangeResponse;

public interface IExchangeService {
    String exchangeInfo(ExchangeRequest request);

    ExchangeResponse exchange(ExchangeRequest request);

    String updateCoins(int value, int quantity);

    String resetMachine();
}
