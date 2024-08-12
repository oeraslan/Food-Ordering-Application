package com.oeraslan.foodorderingapplication.controller;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.DinnerTableResponseDto;
import com.oeraslan.foodorderingapplication.enums.Status;
import com.oeraslan.foodorderingapplication.service.DinnerTableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DinnerTableControllerTest {

    @InjectMocks
    private DinnerTableController dinnerTableController;

    @Mock
    private DinnerTableService dinnerTableService;

    private DinnerTableReserveDto dinnerTableReserveDto;
    private DinnerTableResponseDto dinnerTableResponseDto;

    @BeforeEach
    void setUp() {
        dinnerTableReserveDto = DinnerTableReserveDto.builder()
                .status(Status.RESERVED)
                .orderIds(List.of(1L, 2L))
                .build();

        dinnerTableResponseDto = DinnerTableResponseDto.builder()
                .dinnerTableId(1L)
                .build();
    }

    @Test
    void createDinnerTable() {
        //when
        doNothing().when(dinnerTableService).createDinnerTable(anyInt());
        //then
        dinnerTableController.createDinnerTable(5);

        verify(dinnerTableService, times(1)).createDinnerTable(anyInt());
    }

    @Test
    void updateDinnerTable() {
        //when
        doNothing().when(dinnerTableService).updateDinnerTable(anyLong(), any(DinnerTableReserveDto.class));
        //then
        dinnerTableController.updateDinnerTable(1L, dinnerTableReserveDto);

        verify(dinnerTableService, times(1)).updateDinnerTable(anyLong(), any(DinnerTableReserveDto.class));
    }

    @Test
    void deleteDinnerTable() {
        //when
        doNothing().when(dinnerTableService).deleteDinnerTable(anyLong());
        //then
        dinnerTableController.deleteDinnerTable(1L);

        verify(dinnerTableService, times(1)).deleteDinnerTable(anyLong());
    }

    @Test
    void getDinnerTableById() {
        //when
        when(dinnerTableService.getDinnerTableById(1L)).thenReturn(dinnerTableResponseDto);
        //then
        ResponseEntity<DinnerTableResponseDto> response = dinnerTableController.getDinnerTableById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dinnerTableService, times(1)).getDinnerTableById(anyLong());
        assertInstanceOf(DinnerTableResponseDto.class, response.getBody());
    }

    @Test
    void getAllDinnerTables() {
        //when
        when(dinnerTableService.getAllDinnerTables()).thenReturn(List.of(dinnerTableResponseDto));
        //then
        ResponseEntity<Object> response = dinnerTableController.getAllDinnerTables();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dinnerTableService, times(1)).getAllDinnerTables();
    }

    @Test
    void getAvailableDinnerTables() {
        //when
        when(dinnerTableService.getDinnerTablesNotReserved()).thenReturn(List.of(dinnerTableResponseDto));
        //then
        ResponseEntity<Object> response = dinnerTableController.getAvailableDinnerTables();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dinnerTableService, times(1)).getDinnerTablesNotReserved();
    }

}
