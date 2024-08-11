package com.oeraslan.foodorderingapplication.service;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.DinnerTableResponseDto;
import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;

import java.util.List;

public interface DinnerTableService {

    void createDinnerTable(int numberOfTables);

    void updateDinnerTable(Long id, DinnerTableReserveDto dinnerTableReserveDto);

    void deleteDinnerTable(Long id);

    DinnerTableResponseDto getDinnerTableById(Long id);

    List<DinnerTableResponseDto> getAllDinnerTables();

    List<DinnerTableResponseDto> getDinnerTablesNotReserved();

    DinnerTable findDinnerTableById(Long id);

}
