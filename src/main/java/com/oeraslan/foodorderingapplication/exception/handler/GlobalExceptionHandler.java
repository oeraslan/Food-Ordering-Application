package com.oeraslan.foodorderingapplication.exception.handler;

import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotCreatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotUpdatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodNotCreatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodNotFoundException;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodNotUpdatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotCreatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotFoundException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotUpdatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotCreatedException.class)
    public ResponseEntity<Object> handleOrderNotCreatedException(OrderNotCreatedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(OrderNotUpdatedException.class)
    public ResponseEntity<Object> handleOrderNotUpdatedException(OrderNotUpdatedException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(OrderAlreadyDeletedException.class)
    public ResponseEntity<Object> handleOrderAlreadyDeletedException(OrderAlreadyDeletedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FoodAlreadyDeletedException.class)
    public ResponseEntity<Object> handleFoodAlreadyDeletedException(FoodAlreadyDeletedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FoodNotCreatedException.class)
    public ResponseEntity<Object> handleFoodNotCreatedException(FoodNotCreatedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FoodNotUpdatedException.class)
    public ResponseEntity<Object> handleFoodNotUpdatedException(FoodNotUpdatedException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<Object> handleFoodNotFoundException(FoodNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DinnerTableAlreadyDeletedException.class)
    public ResponseEntity<Object> handleDinnerTableAlreadyDeletedException(DinnerTableAlreadyDeletedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DinnerTableNotCreatedException.class)
    public ResponseEntity<Object> handleDinnerTableNotCreatedException(DinnerTableNotCreatedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DinnerTableNotUpdatedException.class)
    public ResponseEntity<Object> handleDinnerTableNotUpdatedException(DinnerTableNotUpdatedException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

}
