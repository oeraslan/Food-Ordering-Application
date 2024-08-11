package com.oeraslan.foodorderingapplication.enums;

public enum Category {
    DRINKS,
    MAIN_COURSE,
    DESSERT,
    SALAD,
    SNACKS,
    SOUP;

    public static Category fromString(String category) {
        for (Category c : Category.values()) {
            if (c.name().equalsIgnoreCase(category)) {
                return c;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Category.class.getCanonicalName() + "." + category);
    }
}
