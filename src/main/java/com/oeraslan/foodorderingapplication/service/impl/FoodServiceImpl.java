package com.oeraslan.foodorderingapplication.service.impl;

import com.oeraslan.foodorderingapplication.dto.FoodCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.FoodResponseDto;
import com.oeraslan.foodorderingapplication.repository.FoodRepository;
import com.oeraslan.foodorderingapplication.repository.entity.Food;
import com.oeraslan.foodorderingapplication.repository.mapper.FoodMapper;
import com.oeraslan.foodorderingapplication.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public void createFood(FoodCreateOrUpdateDto foodCreateDto) {
        log.info("[{}][createFood] -> request: {}", this.getClass().getSimpleName(), foodCreateDto);

        Food food = FoodMapper.createFood(foodCreateDto);
        foodRepository.save(food);

        log.info("[{}][createFood] -> food created: {}", this.getClass().getSimpleName(), food);
    }

    @Override
    public void updateFood(Long id, FoodCreateOrUpdateDto foodUpdateDto) {
        log.info("[{}][updateFood] -> request: {}", this.getClass().getSimpleName(), foodUpdateDto);

        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
        Food updatedFood = FoodMapper.updateFood(foodUpdateDto, food);
        foodRepository.save(updatedFood);

        log.info("[{}][updateFood] -> food updated: {}", this.getClass().getSimpleName(), updatedFood);

    }

    @Override
    public void deleteFood(Long id) {
        log.info("[{}][deleteFood] -> request id: {}", this.getClass().getSimpleName(), id);

        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
        food.setDeleted(true);
        foodRepository.save(food);
    }

    @Override
    public FoodResponseDto getFoodById(Long id) {
        log.info("[{}][getFoodById] -> request id: {}", this.getClass().getSimpleName(), id);

        return FoodMapper.foodToFoodResponseDto(foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found")));
    }

    @Override
    public List<FoodResponseDto> getAllFoods() {
        log.info("[{}][getAllFoods] -> request", this.getClass().getSimpleName());

        return foodRepository.findAll().stream().map(FoodMapper::foodToFoodResponseDto).toList();
    }
}
