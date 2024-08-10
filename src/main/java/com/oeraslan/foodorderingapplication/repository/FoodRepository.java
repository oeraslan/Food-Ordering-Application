package com.oeraslan.foodorderingapplication.repository;

import com.oeraslan.foodorderingapplication.enums.Category;
import com.oeraslan.foodorderingapplication.repository.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByCategory(Category category);
}
