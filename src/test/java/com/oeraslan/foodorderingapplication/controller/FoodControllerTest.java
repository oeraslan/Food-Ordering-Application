package com.oeraslan.foodorderingapplication.controller;

import com.oeraslan.foodorderingapplication.dto.FoodCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.FoodResponseDto;
import com.oeraslan.foodorderingapplication.enums.Category;
import com.oeraslan.foodorderingapplication.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoodControllerTest {

    @InjectMocks
    private FoodController foodController;

    @Mock
    private FoodService foodService;

    private FoodCreateOrUpdateDto foodCreateOrUpdateDto;
    private FoodResponseDto foodResponseDto;

    @BeforeEach
    void setUp() {
        foodCreateOrUpdateDto = FoodCreateOrUpdateDto.builder()
                .name("Test Food")
                .category(Category.MAIN_COURSE)
                .price(10.0)
                .build();
        foodResponseDto = FoodResponseDto.builder()
                .id(1L)
                .name("Test Food")
                .category(Category.MAIN_COURSE)
                .price(10.0)
                .build();
    }

    @Test
    void createFoodTest() {
        //when
        doNothing().when(foodService).createFood(any(FoodCreateOrUpdateDto.class));
        //then
        foodController.createFood(foodCreateOrUpdateDto);

        verify(foodService, times(1)).createFood(any(FoodCreateOrUpdateDto.class));
    }

    @Test
    void updateFoodTest() {
        //when
        doNothing().when(foodService).updateFood(anyLong(), any(FoodCreateOrUpdateDto.class));
        //then
        foodController.updateFood(foodCreateOrUpdateDto, 1L);

        verify(foodService, times(1)).updateFood(anyLong(), any(FoodCreateOrUpdateDto.class));
    }

    @Test
    void getFoodByIdTest() {
        //when
        when(foodService.getFoodById(anyLong())).thenReturn(foodResponseDto);
        //then
        Object response = foodController.getFoodById(1L).getBody();

        verify(foodService, times(1)).getFoodById(anyLong());
        assertInstanceOf(FoodResponseDto.class, response);
    }

    @Test
    void deleteFoodTest() {
        //when
        doNothing().when(foodService).deleteFood(anyLong());
        //then
        foodController.deleteFood(1L);

        verify(foodService, times(1)).deleteFood(anyLong());
    }

    @Test
    void getAllFoodsTest() {
        //when
        when(foodService.getAllFoods()).thenReturn(List.of(foodResponseDto));
        //then
        foodController.getAllFoods().getBody();

        verify(foodService, times(1)).getAllFoods();
    }

    @Test
    void getFoodsByCategoryTest() {
        //when
        when(foodService.getFoodsByCategory(foodCreateOrUpdateDto.getCategory())).thenReturn(List.of(foodResponseDto));
        //then
        foodController.getFoodsByCategory(foodCreateOrUpdateDto.getCategory().toString()).getBody();

        verify(foodService, times(1)).getFoodsByCategory(any(Category.class));
    }

}
