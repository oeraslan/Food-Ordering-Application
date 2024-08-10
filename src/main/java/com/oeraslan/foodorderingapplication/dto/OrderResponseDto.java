package com.oeraslan.foodorderingapplication.dto;

import com.oeraslan.foodorderingapplication.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Builder
@Data
public class OrderResponseDto {

    private Long id;
    private Long dinnerTableId;
    private HashMap<Long,Integer> foods;
    private Double totalPrice;
    private Status status;
    private String orderCreatedDate;
    private String updatedDate;
}
