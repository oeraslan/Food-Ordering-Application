package com.oeraslan.foodorderingapplication.service.impl;

import com.oeraslan.foodorderingapplication.dto.FoodCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.FoodResponseDto;
import com.oeraslan.foodorderingapplication.enums.Category;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodNotCreatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodNotFoundException;
import com.oeraslan.foodorderingapplication.exception.exceptions.FoodNotUpdatedException;
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

        try {

            Food food = FoodMapper.createFood(foodCreateDto);
            foodRepository.save(food);

            log.info("[{}][createFood] -> food created: {}", this.getClass().getSimpleName(), food);
        } catch (Exception e) {

            log.error("[{}][createFood] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new FoodNotCreatedException("Food not created: " + e.getMessage());
        }

    }

    @Override
    public void updateFood(Long id, FoodCreateOrUpdateDto foodUpdateDto) {
        log.info("[{}][updateFood] -> request: {}", this.getClass().getSimpleName(), foodUpdateDto);

        Food food = findFoodById(id);

        try {

            Food updatedFood = FoodMapper.updateFood(foodUpdateDto, food);
            foodRepository.save(updatedFood);

            log.info("[{}][updateFood] -> food updated: {}", this.getClass().getSimpleName(), updatedFood);
        } catch (Exception e) {

            log.error("[{}][updateFood] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new FoodNotUpdatedException("Food not updated with id: " + id + ", error: " + e.getMessage());
        }

    }

    @Override
    public void deleteFood(Long id) {
        log.info("[{}][deleteFood] -> request id: {}", this.getClass().getSimpleName(), id);

        Food food = findFoodById(id);

        if (food.isDeleted()) {
            throw new FoodAlreadyDeletedException("Food already deleted with id: " + id);
        }

        try {

            food.setDeleted(true);
            foodRepository.save(food);

            log.info("[{}][deleteFood] -> food deleted: {}", this.getClass().getSimpleName(), food);
        } catch (Exception e) {

            log.error("[{}][deleteFood] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new FoodNotUpdatedException("Food not deleted with id: " + id + ", error: " + e.getMessage());
        }

    }

    @Override
    public FoodResponseDto getFoodById(Long id) {
        log.info("[{}][getFoodById] -> request id: {}", this.getClass().getSimpleName(), id);

        return FoodMapper.foodToFoodResponseDto(findFoodById(id));
    }

    @Override
    public List<FoodResponseDto> getAllFoods() {
        log.info("[{}][getAllFoods] -> request", this.getClass().getSimpleName());

        return foodRepository.findAll().stream().map(FoodMapper::foodToFoodResponseDto).toList();
    }

    @Override
    public List<FoodResponseDto> getFoodsByCategory(Category category) {
        log.info("[{}][getFoodsByCategory] -> request category: {}", this.getClass().getSimpleName(), category);

        return foodRepository.findAllByCategory(category).stream().map(FoodMapper::foodToFoodResponseDto).toList();
    }

    private Food findFoodById(Long id) {
        return foodRepository.findById(id).orElseThrow(() -> new FoodNotFoundException("Food not found with id: " + id));
    }

}
