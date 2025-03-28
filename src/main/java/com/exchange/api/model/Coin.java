package com.exchange.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Coin {
    private int currencyValue;
    private int quantity;
}