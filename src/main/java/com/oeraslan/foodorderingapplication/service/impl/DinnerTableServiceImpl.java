package com.oeraslan.foodorderingapplication.service.impl;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.DinnerTableResponseDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.enums.Status;
import com.oeraslan.foodorderingapplication.repository.DinnerTableRepository;
import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;
import com.oeraslan.foodorderingapplication.service.DinnerTableService;
import com.oeraslan.foodorderingapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DinnerTableServiceImpl implements DinnerTableService {

    private final DinnerTableRepository dinnerTableRepository;

    private final OrderService orderService;

    @Override
    public void createDinnerTable(int numberOfTables) {
        log.info("[{}][createDinnerTable] -> numberOfTables: {}", this.getClass().getSimpleName(), numberOfTables);

        for (int i = 0; i < numberOfTables; i++) {
            DinnerTable dinnerTable = new DinnerTable();
            dinnerTable.setStatus(Status.NOT_RESERVED);
            dinnerTable.setCreatedDate(new Date());
            dinnerTable.setUpdatedDate(new Date());
            dinnerTableRepository.save(dinnerTable);
        }

        log.info("[{}][createDinnerTable] -> {} tables created", this.getClass().getSimpleName(), numberOfTables);
    }

    @Override
    public void updateDinnerTable(Long id, DinnerTableReserveDto dinnerTableReserveDto) {
        log.info("[{}][updateDinnerTable] -> request id: {}", this.getClass().getSimpleName(), id);

        DinnerTable dinnerTable = dinnerTableRepository.findById(id).orElseThrow(() -> new RuntimeException("Dinner table not found"));
        dinnerTable.setStatus(dinnerTableReserveDto.getStatus());
        dinnerTable.setUpdatedDate(new Date());
        dinnerTable.setOrderIds(dinnerTableReserveDto.getOrderIds());
        dinnerTableRepository.save(dinnerTable);

        log.info("[{}][updateDinnerTable] -> dinner table updated", this.getClass().getSimpleName());
    }

    @Override
    public void deleteDinnerTable(Long id) {
        log.info("[{}][deleteDinnerTable] -> request id: {}", this.getClass().getSimpleName(), id);

        dinnerTableRepository.deleteById(id);

        log.info("[{}][deleteDinnerTable] ->  dinner table deleted", this.getClass().getSimpleName());
    }


    @Override
    public DinnerTableResponseDto getDinnerTableById(Long id) {
        log.info("[{}][getDinnerTableById] -> request id: {}", this.getClass().getSimpleName(), id);

        DinnerTable dinnerTable = dinnerTableRepository.findById(id).orElseThrow(() -> new RuntimeException("Dinner table not found"));

        return DinnerTableResponseDto.builder()
                .dinnerTableId(dinnerTable.getId())
                .orderIds(dinnerTable.getOrderIds())
                .status(dinnerTable.getStatus())
                .createdDate(dinnerTable.getCreatedDate().toString())
                .updatedDate(dinnerTable.getUpdatedDate().toString())
                .totalPrice(getTotalPrice(dinnerTable.getOrderIds()))
                .build();

    }

    @Override
    public List<DinnerTableResponseDto> getAllDinnerTables() {
        log.info("[{}][getAllDinnerTables] -> request", this.getClass().getSimpleName());

        return dinnerTableRepository.findAll().stream()
                .map(dinnerTable -> DinnerTableResponseDto.builder()
                        .dinnerTableId(dinnerTable.getId())
                        .orderIds(dinnerTable.getOrderIds())
                        .status(dinnerTable.getStatus())
                        .createdDate(dinnerTable.getCreatedDate().toString())
                        .updatedDate(dinnerTable.getUpdatedDate().toString())
                        .totalPrice(getTotalPrice(dinnerTable.getOrderIds()))
                        .build())
                .toList();
    }

    @Override
    public List<DinnerTableResponseDto> getDinnerTablesNotReserved() {
        log.info("[{}][getDinnerTablesNotReserved] -> request", this.getClass().getSimpleName());

        return dinnerTableRepository.findAll().stream().map(
                        dinnerTable -> DinnerTableResponseDto.builder()
                                .dinnerTableId(dinnerTable.getId())
                                .orderIds(dinnerTable.getOrderIds())
                                .status(dinnerTable.getStatus())
                                .createdDate(dinnerTable.getCreatedDate().toString())
                                .updatedDate(dinnerTable.getUpdatedDate().toString())
                                .totalPrice(getTotalPrice(dinnerTable.getOrderIds()))
                                .build())
                .filter(dinnerTableResponseDto -> dinnerTableResponseDto.getStatus().equals(Status.NOT_RESERVED))
                .toList(
                );
    }

    private Double getTotalPrice(List<Long> orderIds) {
        return orderIds.stream()
                .map(orderService::getOrderById)
                .mapToDouble(OrderResponseDto::getTotalPrice)
                .sum();
    }

}
