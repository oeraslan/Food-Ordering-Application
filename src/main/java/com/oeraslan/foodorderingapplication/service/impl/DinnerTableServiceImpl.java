package com.oeraslan.foodorderingapplication.service.impl;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.DinnerTableResponseDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.enums.Status;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotCreatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotFoundException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotUpdatedException;
import com.oeraslan.foodorderingapplication.repository.DinnerTableRepository;
import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;
import com.oeraslan.foodorderingapplication.service.DinnerTableService;
import com.oeraslan.foodorderingapplication.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DinnerTableServiceImpl implements DinnerTableService {

    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Autowired
    @Lazy
    private OrderService orderService;

    @Override
    public void createDinnerTable(int numberOfTables) {
        log.info("[{}][createDinnerTable] -> numberOfTables: {}", this.getClass().getSimpleName(), numberOfTables);

        try {
            for (int i = 0; i < numberOfTables; i++) {
                DinnerTable dinnerTable = new DinnerTable();
                dinnerTable.setStatus(Status.NOT_RESERVED);
                dinnerTable.setCreatedDate(new Date());
                dinnerTable.setUpdatedDate(new Date());
                dinnerTableRepository.save(dinnerTable);
            }

            log.info("[{}][createDinnerTable] -> {} tables created", this.getClass().getSimpleName(), numberOfTables);
        } catch (Exception e) {

            log.error("[{}][createDinnerTable] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new DinnerTableNotCreatedException("Dinner table not created: " + e.getMessage());
        }


    }

    @Override
    public void updateDinnerTable(Long id, DinnerTableReserveDto dinnerTableReserveDto) {
        log.info("[{}][updateDinnerTable] -> request id: {}", this.getClass().getSimpleName(), id);

        DinnerTable dinnerTable = findDinnerTableById(id);

        try {
            dinnerTable.setStatus(dinnerTableReserveDto.getStatus());
            dinnerTable.setUpdatedDate(new Date());
            dinnerTable.setOrderIds(dinnerTableReserveDto.getOrderIds());
            dinnerTableRepository.save(dinnerTable);

            log.info("[{}][updateDinnerTable] -> dinner table updated", this.getClass().getSimpleName());
        } catch (Exception e) {

            log.error("[{}][updateDinnerTable] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new DinnerTableNotUpdatedException("Dinner table not updated: " + e.getMessage());
        }

    }

    @Override
    public void deleteDinnerTable(Long id) {
        log.info("[{}][deleteDinnerTable] -> request id: {}", this.getClass().getSimpleName(), id);

        DinnerTable dinnerTable = findDinnerTableById(id);

        if (dinnerTable.isDeleted()) {
            throw new DinnerTableAlreadyDeletedException("Dinner table already deleted with id: " + id);
        }

        try {
            dinnerTable.setDeleted(true);
            dinnerTableRepository.save(dinnerTable);

            log.info("[{}][deleteDinnerTable] ->  dinner table deleted", this.getClass().getSimpleName());
        } catch (Exception e) {

            log.error("[{}][deleteDinnerTable] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new DinnerTableNotUpdatedException("Dinner table not updated with id: " + id + ", error: " + e.getMessage());
        }

    }


    @Override
    public DinnerTableResponseDto getDinnerTableById(Long id) {
        log.info("[{}][getDinnerTableById] -> request id: {}", this.getClass().getSimpleName(), id);

        DinnerTable dinnerTable = findDinnerTableById(id);

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

    @Override
    public DinnerTable findDinnerTableById(Long id) {
        return dinnerTableRepository.findById(id).orElseThrow(() -> new DinnerTableNotFoundException("Dinner table not found"));
    }

    private Double getTotalPrice(List<Long> orderIds) {
        return orderIds.stream()
                .map(orderService::getOrderById)
                .mapToDouble(OrderResponseDto::getTotalPrice)
                .sum();
    }

}
