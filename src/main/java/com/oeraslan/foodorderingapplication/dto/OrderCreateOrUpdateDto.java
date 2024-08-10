package com.oeraslan.foodorderingapplication.dto;

import com.oeraslan.foodorderingapplication.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class OrderCreateOrUpdateDto {

    private Long dinnerTableId;
    private Map<Long,Integer> foods;
    private Status status;

}
