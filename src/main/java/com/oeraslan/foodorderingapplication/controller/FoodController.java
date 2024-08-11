package com.oeraslan.foodorderingapplication.controller;

import com.oeraslan.foodorderingapplication.dto.FoodCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.enums.Category;
import com.oeraslan.foodorderingapplication.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createFood(@RequestBody FoodCreateOrUpdateDto foodCreateDto) {
        log.info("[{}][createFood] -> creating food", this.getClass().getSimpleName());

        foodService.createFood(foodCreateDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateFood(@RequestBody FoodCreateOrUpdateDto foodUpdateDto, @PathVariable Long id) {
        log.info("[{}][updateFood] -> updating food", this.getClass().getSimpleName());

        foodService.updateFood(id,foodUpdateDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getFoodById(@PathVariable Long id) {
        log.info("[{}][getFoodById] -> getting food", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(foodService.getFoodById(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFood(@PathVariable Long id) {
        log.info("[{}][deleteFood] -> deleting food", this.getClass().getSimpleName());

        foodService.deleteFood(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllFoods() {
        log.info("[{}][getAllFoods] -> getting all foods", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(foodService.getAllFoods());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Object> getFoodsByCategory(@PathVariable String category) {
        log.info("[{}][getFoodsByCategory] -> getting foods by category", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(foodService.getFoodsByCategory(Category.fromString(category)));
    }

}
