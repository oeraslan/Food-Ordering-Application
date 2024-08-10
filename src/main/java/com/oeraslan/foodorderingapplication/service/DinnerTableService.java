package com.oeraslan.foodorderingapplication.service;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.DinnerTableResponseDto;

import java.util.List;

public interface DinnerTableService {

    void createDinnerTable(int numberOfTables);

    void updateDinnerTable(DinnerTableReserveDto dinnerTableReserveDto);

    void deleteDinnerTable(Long id);

    void reserveDinnerTable(DinnerTableReserveDto dinnerTableReserveDto);

    void cancelDinnerTableReservation(Long id);

    DinnerTableResponseDto getDinnerTableById(Long id);

    List<DinnerTableResponseDto> getAllDinnerTables();

    List<DinnerTableResponseDto> getDinnerTablesNotReserved();

}
