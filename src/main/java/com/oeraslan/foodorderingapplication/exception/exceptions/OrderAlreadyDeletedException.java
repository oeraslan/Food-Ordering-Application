package com.oeraslan.foodorderingapplication.exception.exceptions;

public class OrderAlreadyDeletedException extends RuntimeException {
    public OrderAlreadyDeletedException(String message) {
        super(message);
    }
}
