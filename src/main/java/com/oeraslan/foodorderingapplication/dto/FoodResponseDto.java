package com.oeraslan.foodorderingapplication.dto;

import com.oeraslan.foodorderingapplication.enums.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodResponseDto {
        private Long id;
        private String name;
        private String description;
        //Todo add image field :private byte[] image;
        private Double price;
        private Category category;
        private String createdDate;
        private String updatedDate;
}
