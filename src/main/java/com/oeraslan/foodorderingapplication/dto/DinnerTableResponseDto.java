package com.oeraslan.foodorderingapplication.dto;

import com.oeraslan.foodorderingapplication.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DinnerTableResponseDto {

    private Long dinnerTableId;
    private List<Long> orderIds;
    private Status status;
    private String createdDate;
    private String updatedDate;
    private Double totalPrice;
}
