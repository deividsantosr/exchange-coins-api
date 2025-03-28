package com.exchange.api.service;

import com.exchange.api.model.Coin;
import com.exchange.api.model.ExchangeRequest;
import com.exchange.api.model.ExchangeResponse;
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

        return ExchangeUtils.formatExchangeResponse(exchangeResponse);
    }

    public ExchangeResponse exchange(ExchangeRequest request) {
        if (!machineRunning) {
            return buildErrorResponse("The machine is out of order. There are not enough coins available.");
        }

        BigDecimal amount = request.getValue();
        int amountInCents = amount.multiply(BigDecimal.valueOf(100)).intValue();
        List<Coin> coinsUsed = new ArrayList<>();
        int remainingAmount = amountInCents;

        sortCoins(request.isMaximizeCoins());

        for (Coin coin : coins) {
            int coinsToUse = Math.min(remainingAmount / coin.getCurrencyValue(), coin.getQuantity());
            if (coinsToUse > 0) {
                remainingAmount -= coinsToUse * coin.getCurrencyValue();
                coinsUsed.add(Coin.builder().currencyValue(coin.getCurrencyValue()).quantity(coinsToUse).build());
                coin.setQuantity(coin.getQuantity() - coinsToUse);
            }
        }

        if (remainingAmount > 0) {
            return buildErrorResponse("There are not enough coins to complete the transaction.");
        }

        checkMachineStatus();

        return buildSuccessResponse(amount, coinsUsed);
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

    private ExchangeResponse buildErrorResponse(String message) {
        return ExchangeResponse.builder()
                .success(false)
                .message(message)
                .usedCoins(new ArrayList<>())
                .currentCoins(coins)
                .currentNotes(notes)
                .totalCoins(BigDecimal.ZERO)
                .build();
    }

    private ExchangeResponse buildSuccessResponse(BigDecimal amount, List<Coin> coinsUsed) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            notes.add(amount);
        }

        String coinsUsedSummary = buildCoinsUsedSummary(coinsUsed);
        String machineStateSummary = buildMachineStateSummary();
        BigDecimal notesTotal = notes.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal coinTotal = coins.stream()
                .map(coin -> BigDecimal.valueOf(coin.getQuantity() * coin.getCurrencyValue() / 100.0))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal total = coinTotal.add(notesTotal);

        return ExchangeResponse.builder()
                .success(true)
                .message("Exchanged $" + amount + " successfully!")
                .summary(coinsUsedSummary)
                .usedCoins(coinsUsed)
                .currentCoins(coins)
                .currentNotes(notes)
                .totalCoins(coinTotal)
                .totalNotes(notesTotal)
                .total(total)
                .state("Bills amount: $" + notesTotal + "\nCoins amount: $" + coinTotal + "\n" + machineStateSummary + "Total amount: $" + total)
                .build();
    }

    private String buildCoinsUsedSummary(List<Coin> coinsUsed) {
        StringBuilder summary = new StringBuilder();
        for (Coin coin : coinsUsed) {
            summary.append("- ")
                    .append(coin.getQuantity())
                    .append(" coins (")
                    .append(coin.getCurrencyValue())
                    .append(coin.getCurrencyValue() == 1 ? " cent) = $" : " cents) = $")
                    .append(coin.getQuantity() * coin.getCurrencyValue() / 100.0)
                    .append("\n");
        }

        return summary.toString();
    }

    private String buildMachineStateSummary() {
        StringBuilder summary = new StringBuilder();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Coin coin : coins) {
            if (coin.getQuantity() > 0) {
                summary.append("- ")
                        .append(coin.getQuantity())
                        .append(" coins (")
                        .append(coin.getCurrencyValue())
                        .append(coin.getCurrencyValue() == 1 ? " cent) = $" : " cents) = $")
                        .append(coin.getQuantity() * coin.getCurrencyValue() / 100.0)
                        .append(")\n");
            }

            totalAmount = totalAmount.add(BigDecimal.valueOf(coin.getQuantity() * coin.getCurrencyValue() / 100.0));
        }

        return summary.toString();
    }
}
