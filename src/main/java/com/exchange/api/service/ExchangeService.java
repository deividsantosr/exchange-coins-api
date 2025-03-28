package com.exchange.api.service;

import com.exchange.api.model.Coin;
import com.exchange.api.model.ExchangeRequest;
import com.exchange.api.model.ExchangeResponse;
import com.exchange.api.service.builder.ExchangeResponseBuilder;
import com.exchange.api.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.exchange.api.utils.ExchangeUtils.INITIAL_COIN_QUANTITY;

@Service
public class ExchangeService implements IExchangeService {
    private final List<Coin> coins = new ArrayList<>();
    private final List<BigDecimal> notes = new ArrayList<>();
    private boolean machineRunning = true;

    public ExchangeService() {
        initialValues();
    }

    public String exchangeInfo(ExchangeRequest request) {
        ExchangeResponse exchangeResponse = exchange(request);

        return ExchangeResponseBuilder.buildExchangeInfo(exchangeResponse);
    }

    public ExchangeResponse exchange(ExchangeRequest request) {
        if (!machineRunning) {
            return ExchangeResponseBuilder.buildError("The machine is out of order. There are not enough coins available.", coins, notes);
        }

        BigDecimal amount = request.getValue();
        List<Coin> coinsUsed = new ArrayList<>();
        int remainingAmount = amount.multiply(BigDecimal.valueOf(100)).intValue();

        if (isEnoughCurrencyAvailable(amount)) {
            return ExchangeResponseBuilder.buildError("There are not enough coins to complete the transaction.", coins, notes);
        }

        sortCoins(request.isMaximizeCoins());

        for (Coin coin : coins) {
            int coinsToUse = Math.min(remainingAmount / coin.getCurrencyValue(), coin.getQuantity());
            if (coinsToUse > 0) {
                remainingAmount -= coinsToUse * coin.getCurrencyValue();
                coinsUsed.add(Coin.builder().currencyValue(coin.getCurrencyValue()).quantity(coinsToUse).build());
                coin.setQuantity(coin.getQuantity() - coinsToUse);
            }
        }

        checkMachineStatus();

        return ExchangeResponseBuilder.buildSuccess(amount, coinsUsed, coins, notes);
    }

    public String updateCoins(int value, int quantity) {
        coins.stream()
                .filter(coin -> coin.getCurrencyValue() == value)
                .findFirst()
                .ifPresent(coin -> coin.setQuantity(coin.getQuantity() + quantity));

        machineRunning = true;

        return "Coin updated successfully!";
    }

    public String resetMachine() {
        initialValues();
        machineRunning = true;

        return "Machine reset successfully!";
    }

    private void initialValues() {
        notes.clear();
        coins.clear();

        for (int value : new int[]{25, 10, 5, 1}) {
            coins.add(Coin.builder().currencyValue(value).quantity(INITIAL_COIN_QUANTITY).build());
        }
    }

    private void checkMachineStatus() {
        machineRunning = coins.stream().anyMatch(coin -> coin.getQuantity() > 0);
    }

    private void sortCoins(boolean maximizeCoins) {
        coins.sort(maximizeCoins
                ? Comparator.comparingInt(Coin::getCurrencyValue)
                : Comparator.comparingInt(Coin::getCurrencyValue).reversed());
    }

    private boolean isEnoughCurrencyAvailable(BigDecimal amount) {
        return amount.compareTo(ExchangeUtils.getTotalCoins(coins)) > 0;
    }
}


