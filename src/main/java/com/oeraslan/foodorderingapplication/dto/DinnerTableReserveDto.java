package com.oeraslan.foodorderingapplication.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DinnerTableReserveDto {

    private Long dinnerTableId;
    private List<Long> orderId;

}
