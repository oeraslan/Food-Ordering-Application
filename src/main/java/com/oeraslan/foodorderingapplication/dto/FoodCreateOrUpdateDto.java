package com.oeraslan.foodorderingapplication.dto;

import com.oeraslan.foodorderingapplication.enums.Category;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FoodCreateOrUpdateDto {

        private String name;
        private Double price;
        private String description;
        private Category category;
        // Todo :private byte[] image;
}
