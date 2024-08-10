package com.oeraslan.foodorderingapplication.dto;

import com.oeraslan.foodorderingapplication.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DinnerTableReserveDto {

    private List<Long> orderIds;
    private Status status;

}
