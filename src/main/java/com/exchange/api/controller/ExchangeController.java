package com.exchange.api.controller;

import com.exchange.api.model.ExchangeRequest;
import com.exchange.api.model.ExchangeResponse;
import com.exchange.api.service.IExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exchange")
public class ExchangeController {

    @Autowired
    private IExchangeService exchangeService;

    @Operation(summary = "Get exchange info", description = "Retrieve the exchange information based on the provided request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved exchange information")
    })
    @PostMapping("/exchange-info")
    public ResponseEntity<String> exchangeInfo(
            @Parameter(description = "Request containing the details for the exchange info.")
            @RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(exchangeService.exchangeInfo(request));
    }

    @Operation(summary = "Perform an exchange", description = "Perform the coin exchange based on the provided request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully performed exchange")
    })
    @PostMapping("/exchange")
    public ResponseEntity<ExchangeResponse> exchange(
            @Parameter(description = "Request containing the details for the exchange.")
            @RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(exchangeService.exchange(request));
    }

    @Operation(summary = "Reset the exchange machine", description = "Reset the exchange machine to its initial state.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reset the machine")
    })
    @PatchMapping("/reset")
    public ResponseEntity<String> reset() {
        return ResponseEntity.ok(exchangeService.resetMachine());
    }

    @Operation(summary = "Update coins in the machine", description = "Update the quantity of a specific coin in the machine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the coins")
    })
    @PatchMapping("/update-coins")
    public ResponseEntity<String> updateCoins(
            @Parameter(description = "The currency value of the coin to be updated.")
            @RequestParam int currencyValue,
            @Parameter(description = "The quantity of the coin to be added.")
            @RequestParam int quantity) {
        return ResponseEntity.ok(exchangeService.updateCoins(currencyValue, quantity));
    }
}
