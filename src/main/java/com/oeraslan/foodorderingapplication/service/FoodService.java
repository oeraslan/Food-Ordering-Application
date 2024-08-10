package com.oeraslan.foodorderingapplication.service;

import com.oeraslan.foodorderingapplication.dto.FoodCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.FoodResponseDto;

import java.util.List;

public interface FoodService {

    void createFood(FoodCreateOrUpdateDto foodCreateDto);

    void updateFood(Long id, FoodCreateOrUpdateDto foodUpdateDto);

    void deleteFood(Long id);

    FoodResponseDto getFoodById(Long id);

    List<FoodResponseDto> getAllFoods();
}
