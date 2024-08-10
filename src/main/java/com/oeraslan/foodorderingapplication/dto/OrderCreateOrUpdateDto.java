package com.oeraslan.foodorderingapplication.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class OrderCreateOrUpdateDto {

    private Long dinnerTableId;
    private Map<Long,Integer> foods;

}
