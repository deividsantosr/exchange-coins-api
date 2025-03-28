package com.exchange.api.service.builder;

import com.exchange.api.model.Coin;
import com.exchange.api.model.ExchangeResponse;
import com.exchange.api.utils.ExchangeUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExchangeResponseBuilder {
    public static ExchangeResponse buildSuccess(BigDecimal amount, List<Coin> coinsUsed, List<Coin> coins, List<BigDecimal> notes) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            notes.add(amount);
        }

        String coinsUsedSummary = buildCoinsUsedSummary(coinsUsed).toString();
        String machineStateSummary = buildMachineState(coins).toString();
        BigDecimal totalNotes = ExchangeUtils.getTotalNotes(notes);
        BigDecimal totalCoins = ExchangeUtils.getTotalCoins(coins);
        BigDecimal total = ExchangeUtils.getTotal(totalCoins, totalNotes);

        return ExchangeResponse.builder()
                .success(true)
                .message("Exchanged $" + amount + " successfully!")
                .summary(coinsUsedSummary)
                .usedCoins(coinsUsed)
                .currentCoins(coins)
                .currentNotes(notes)
                .totalCoins(totalCoins)
                .totalNotes(totalNotes)
                .total(total)
                .state("Bills amount: $" + totalNotes + "\nCoins amount: $" + totalCoins + "\n" + machineStateSummary + "Total amount: $" + total)
                .build();
    }

    public static ExchangeResponse buildError(String message, List<Coin> coins, List<BigDecimal> notes) {
        BigDecimal totalNotes = ExchangeUtils.getTotalNotes(notes);
        BigDecimal totalCoins = ExchangeUtils.getTotalCoins(coins);
        BigDecimal total = ExchangeUtils.getTotal(totalCoins, totalNotes);

        return ExchangeResponse.builder()
                .success(false)
                .message(message)
                .usedCoins(new ArrayList<>())
                .currentCoins(coins)
                .currentNotes(notes)
                .totalCoins(totalCoins)
                .totalNotes(totalNotes)
                .total(total)
                .build();
    }

    public static String buildExchangeInfo(ExchangeResponse exchangeResponse) {
        return exchangeResponse.getMessage()
                .concat("\n")
                .concat("\nStatement:\n")
                .concat(exchangeResponse.getSummary() != null ? exchangeResponse.getSummary() : "N/A")
                .concat("\n")
                .concat(exchangeResponse.getState() != null ? exchangeResponse.getState() : "N/A");
    }

    public static StringBuilder buildCoinsUsedSummary(List<Coin> coinsUsed) {
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

        return summary;
    }

    public static StringBuilder buildMachineState(List<Coin> coins) {
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

        return summary;
    }

}