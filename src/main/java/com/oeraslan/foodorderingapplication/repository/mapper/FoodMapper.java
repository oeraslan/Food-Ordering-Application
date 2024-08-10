package com.oeraslan.foodorderingapplication.repository.mapper;

import com.oeraslan.foodorderingapplication.dto.FoodCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.FoodResponseDto;
import com.oeraslan.foodorderingapplication.repository.entity.Food;

import java.util.Date;
import java.util.Optional;

public interface FoodMapper {

    static Food createFood(FoodCreateOrUpdateDto foodCreateDto) {
        return Food.builder()
                .name(foodCreateDto.getName())
                .price(foodCreateDto.getPrice())
                .description(foodCreateDto.getDescription())
                .category(foodCreateDto.getCategory())
                .createdDate(new Date())
                .updatedDate(new Date())
                .deleted(false)
                .build();
    }

    static Food updateFood(FoodCreateOrUpdateDto foodUpdateDto, Food food) {
        Optional.ofNullable(foodUpdateDto.getName())
                .filter(name -> !name.isEmpty())
                .ifPresent(food::setName);

        Optional.ofNullable(foodUpdateDto.getPrice())
                .filter(price -> price > 0)
                .ifPresent(food::setPrice);

        Optional.ofNullable(foodUpdateDto.getDescription())
                .filter(description -> !description.isEmpty())
                .ifPresent(food::setDescription);

        Optional.ofNullable(foodUpdateDto.getCategory())
                .ifPresent(food::setCategory);

        food.setUpdatedDate(new Date());
        return food;
    }

    static FoodResponseDto foodToFoodResponseDto(Food food) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .name(food.getName())
                .price(food.getPrice())
                .description(food.getDescription())
                .category(food.getCategory())
                .createdDate(food.getCreatedDate().toString())
                .updatedDate(food.getUpdatedDate().toString())
                .build();
    }

}
