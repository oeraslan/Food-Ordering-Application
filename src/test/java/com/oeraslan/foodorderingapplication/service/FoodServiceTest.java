package com.oeraslan.foodorderingapplication.service;

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
import com.oeraslan.foodorderingapplication.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoodServiceTest {
    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodServiceImpl foodService;

    private Food food;
    private FoodCreateOrUpdateDto foodCreateOrUpdateDto;

    @BeforeEach
    void setUp() {
        food = new Food();
        food.setId(1L);
        food.setName("Pizza");
        food.setCategory(Category.MAIN_COURSE);
        food.setDeleted(false);

        foodCreateOrUpdateDto = FoodCreateOrUpdateDto.builder()
                .name("Pizza")
                .category(Category.MAIN_COURSE)
                .price(50.0)
                .build();
    }

    @Test
    void whenCreateFoodCalledWithDto_thenSaveFood() {
        // when
        when(foodRepository.save(any(Food.class))).thenReturn(food);
        // then
        foodService.createFood(foodCreateOrUpdateDto);
        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void whenCreateFoodCalled_thenThrowException() {
        // when
        when(foodRepository.save(any(Food.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(FoodNotCreatedException.class, () -> foodService.createFood(foodCreateOrUpdateDto));
    }

    @Test
    void whenUpdateFoodCalledWithDto_thenUpdateFood() {
        // when
        when(foodRepository.save(any(Food.class))).thenReturn(food);
        when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
        // then
        foodService.updateFood(1L, foodCreateOrUpdateDto);
        verify(foodRepository, times(1)).save(any(Food.class));
        assertEquals(food.getPrice(), foodCreateOrUpdateDto.getPrice());
    }

    @Test
    void whenUpdateFoodCalled_thenThrowException() {
        // when
        when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
        when(foodRepository.save(any(Food.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(FoodNotUpdatedException.class, () -> foodService.updateFood(1L, foodCreateOrUpdateDto));
    }

    @Test
    void whenDeleteFoodCalledWithId_thenDeleteFood() {
        // when
        when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
        // then
        foodService.deleteFood(1L);
        verify(foodRepository, times(1)).save(any(Food.class));
        assertTrue(food.isDeleted());
    }

    @Test
    void whenDeleteFoodCalled_thenThrowFoodAlreadyDeletedException() {
        // when
        when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
        food.setDeleted(true);
        // then
        assertThrows(FoodAlreadyDeletedException.class, () -> foodService.deleteFood(1L));
    }

    @Test
    void whenDeleteFoodCalled_thenThrowFoodNotUpdatedException() {
        // when
        when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
        when(foodRepository.save(any(Food.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(FoodNotUpdatedException.class, () -> foodService.deleteFood(1L));
    }

    @Test
    void whenGetFoodById_thenReturnFood() {
        try (MockedStatic<FoodMapper> mockedStatic = mockStatic(FoodMapper.class)) {
            // when
            when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
            mockedStatic.when(() -> FoodMapper.foodToFoodResponseDto(food)).thenReturn(FoodResponseDto.builder().name(food.getName()).build());
            // then
            assertEquals(food.getName(), foodService.getFoodById(1L).getName());
        }
    }

    @Test
    void whenGetFoodById_thenThrowFoodNotFoundException() {
        // when
        when(foodRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        // then
        assertThrows(FoodNotFoundException.class, () -> foodService.getFoodById(1L));
    }

    @Test
    void whenGetAllFoods_thenReturnListOfFoods() {
        try (MockedStatic<FoodMapper> mockedStatic = mockStatic(FoodMapper.class)) {
            // when
            when(foodRepository.findAll()).thenReturn(java.util.List.of(food));
            // then
            assertEquals(1, foodService.getAllFoods().size());
        }
    }

    @Test
    void whenGetFoodsByCategory_thenReturnListOfFoods() {
        try (MockedStatic<FoodMapper> mockedStatic = mockStatic(FoodMapper.class)) {
            // when
            when(foodRepository.findAllByCategory(Category.MAIN_COURSE)).thenReturn(java.util.List.of(food));
            // then
            assertEquals(1, foodService.getFoodsByCategory(Category.MAIN_COURSE).size());
        }
    }

}
