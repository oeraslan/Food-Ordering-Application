package com.oeraslan.foodorderingapplication.dto;

import com.oeraslan.foodorderingapplication.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class OrderResponseDto {

    private Long id;
    private Long dinnerTableId;
    private Map<Long,Integer> foods;
    private Double totalPrice;
    private Status status;
    private String orderCreatedDate;
    private String updatedDate;
}
