package com.oeraslan.foodorderingapplication.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Builder
@Data
public class OrderCreateOrUpdateDto {

    private Long dinnerTableId;
    private HashMap<Long,Integer> foods;

}
