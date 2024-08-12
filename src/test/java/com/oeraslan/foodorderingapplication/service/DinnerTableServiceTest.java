package com.oeraslan.foodorderingapplication.service;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.enums.Status;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotCreatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotFoundException;
import com.oeraslan.foodorderingapplication.exception.exceptions.DinnerTableNotUpdatedException;
import com.oeraslan.foodorderingapplication.repository.DinnerTableRepository;
import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;
import com.oeraslan.foodorderingapplication.service.impl.DinnerTableServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DinnerTableServiceTest {

    @InjectMocks
    private DinnerTableServiceImpl dinnerTableService;

    @Mock
    private DinnerTableRepository dinnerTableRepository;

    @Mock
    private OrderService orderService;

    private DinnerTable dinnerTable;
    private DinnerTableReserveDto dinnerTableReserveDto;
    private OrderResponseDto order;

    @BeforeEach
    void setUp() {
        dinnerTable = new DinnerTable();
        dinnerTable.setId(1L);
        dinnerTable.setStatus(Status.NOT_RESERVED);
        dinnerTable.setCreatedDate(new Date());
        dinnerTable.setUpdatedDate(new Date());
        dinnerTable.setDeleted(false);
        dinnerTable.setOrderIds(List.of(1L));

        dinnerTableReserveDto = DinnerTableReserveDto.builder()
                .status(Status.RESERVED)
                .build();

        order = OrderResponseDto.builder()
                .id(1L)
                .dinnerTableId(1L)
                .totalPrice(100.0)
                .build();

    }

    @Test
    void whenCreateDinnerTableCalledWithNumberOfTables_thenSaveDinnerTables() {
        // when
        dinnerTableService.createDinnerTable(5);
        // then
        verify(dinnerTableRepository, times(5) ).save(any(DinnerTable.class));
    }

    @Test
    void whenCreateDinnerTableCalledWithNumberOfTables_thenThrowException() {
        // when
        when(dinnerTableRepository.save(any(DinnerTable.class))).thenThrow(new RuntimeException());

        // then
        assertThrows(DinnerTableNotCreatedException.class, () -> dinnerTableService.createDinnerTable(5));
    }

    @Test
    void whenUpdateDinnerTableCalledWithIdAndDto_thenUpdateDinnerTable() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.of(dinnerTable));
        when(dinnerTableRepository.save(any(DinnerTable.class))).thenReturn(dinnerTable);
        // then
        dinnerTableService.updateDinnerTable(1L, dinnerTableReserveDto);
        verify(dinnerTableRepository, times(1)).save(any(DinnerTable.class));
    }

    @Test
    void whenUpdateDinnerTableCalledWithIdAndDto_thenThrowException() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.of(dinnerTable));
        when(dinnerTableRepository.save(any(DinnerTable.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(DinnerTableNotUpdatedException.class, () -> dinnerTableService.updateDinnerTable(1L, dinnerTableReserveDto));
    }

    @Test
    void whenDeleteDinnerTableCalledWithId_thenDeleteDinnerTable() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.of(dinnerTable));
        when(dinnerTableRepository.save(any(DinnerTable.class))).thenReturn(dinnerTable);
        // then
        dinnerTableService.deleteDinnerTable(1L);
        assertTrue(dinnerTable.isDeleted());
    }

    @Test
    void whenDeleteDinnerTableCalledWithId_thenThrowException() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.of(dinnerTable));
        when(dinnerTableRepository.save(any(DinnerTable.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(DinnerTableNotUpdatedException.class, () -> dinnerTableService.deleteDinnerTable(1L));
    }

    @Test
    void whenDeleteDinnerTableCalledWithId_thenThrowDinnerTableAlreadyDeletedException() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.of(dinnerTable));
        dinnerTable.setDeleted(true);
        // then
        assertThrows(DinnerTableAlreadyDeletedException.class, () -> dinnerTableService.deleteDinnerTable(1L));
    }

    @Test
    void whenGetDinnerTableByIdCalled_thenGetDinnerTable() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.of(dinnerTable));
        when(orderService.getOrderById(1L)).thenReturn(order);
        // then
        assertEquals(dinnerTable.getCreatedDate().toString(), dinnerTableService.getDinnerTableById(1L).getCreatedDate());
        assertEquals(order.getTotalPrice(), dinnerTableService.getDinnerTableById(1L).getTotalPrice());
    }

    @Test
    void whenGetAllDinnerTablesCalled_thenGetAllDinnerTables() {
        // when
        when(dinnerTableRepository.findAll()).thenReturn(List.of(dinnerTable));
        when(orderService.getOrderById(1L)).thenReturn(order);
        // then
        assertEquals(dinnerTable.getCreatedDate().toString(), dinnerTableService.getAllDinnerTables().get(0).getCreatedDate());
        assertEquals(order.getTotalPrice(), dinnerTableService.getAllDinnerTables().get(0).getTotalPrice());
    }

    @Test
    void whenGetDinnerTablesNotReservedCalled_thenGetDinnerTablesNotReserved() {
        // when
        when(dinnerTableRepository.findAll()).thenReturn(List.of(dinnerTable));
        when(orderService.getOrderById(1L)).thenReturn(order);
        // then
        assertEquals(dinnerTable.getCreatedDate().toString(), dinnerTableService.getDinnerTablesNotReserved().get(0).getCreatedDate());
        assertEquals(order.getTotalPrice(), dinnerTableService.getDinnerTablesNotReserved().get(0).getTotalPrice());
        assertEquals(Status.NOT_RESERVED, dinnerTableService.getDinnerTablesNotReserved().get(0).getStatus());
    }

    @Test
    void whenFindDinnerTableByIdCalled_thenFindDinnerTableById() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.of(dinnerTable));
        // then
        assertEquals(dinnerTable, dinnerTableService.findDinnerTableById(1L));
    }

    @Test
    void whenFindDinnerTableByIdCalled_thenThrowException() {
        // when
        when(dinnerTableRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        // then
        assertThrows(DinnerTableNotFoundException.class, () -> dinnerTableService.findDinnerTableById(1L));
    }

}
