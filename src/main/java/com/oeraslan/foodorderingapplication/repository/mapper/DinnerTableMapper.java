package com.oeraslan.foodorderingapplication.repository.mapper;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;

public interface DinnerTableMapper {

    static DinnerTableReserveDto dinnerTableToDinnerTableReserveDto(DinnerTable dinnerTable) {
        return DinnerTableReserveDto.builder()
                .orderIds(dinnerTable.getOrderIds())
                .status(dinnerTable.getStatus())
                .build();
    }

}
