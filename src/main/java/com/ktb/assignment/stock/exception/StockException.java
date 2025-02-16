package com.ktb.assignment.stock.exception;

import lombok.Getter;

@Getter
public class StockException extends RuntimeException {
    private final int statusCode;

    public StockException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
