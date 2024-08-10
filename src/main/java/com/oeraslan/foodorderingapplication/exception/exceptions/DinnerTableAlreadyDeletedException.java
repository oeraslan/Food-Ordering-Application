package com.oeraslan.foodorderingapplication.exception.exceptions;

public class DinnerTableAlreadyDeletedException extends RuntimeException {
    public DinnerTableAlreadyDeletedException(String message) {
        super(message);
    }
}
