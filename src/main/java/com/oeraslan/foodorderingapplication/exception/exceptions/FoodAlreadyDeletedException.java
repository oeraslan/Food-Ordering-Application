package com.oeraslan.foodorderingapplication.exception.exceptions;

public class FoodAlreadyDeletedException extends RuntimeException {
    public FoodAlreadyDeletedException(String message) {
        super(message);
    }
}
