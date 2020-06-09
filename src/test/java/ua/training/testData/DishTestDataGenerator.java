package ua.training.testData;

import ua.training.dto.DishDto;
import ua.training.entity.Category;
import ua.training.entity.Dish;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DishTestDataGenerator {

    private DishTestDataGenerator() {

    }

    public static List<Dish> generateDishesList() {
        return new ArrayList<Dish>() {
            {
                add(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                        .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                        .build());
                add(new Dish.Builder().setId(1L).setName("chicken").setDescription("with vegetables")
                        .setWeight(180).setCost(new BigDecimal("190.5")).setCategory(new Category.Builder().setId(2L).setName("meat").build())
                        .build());
            }
        };
    }

    public static Optional<Dish> generateOptionalDish() {
        return Optional.of(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                .build());
    }

    public static DishDto generateDishForCreation() {
        return new DishDto.Builder().setName("cheesecake").setDescription("delicious")
                .setWeight("150").setCost("180.5").setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                .build();
    }

    public static DishDto generateInvalidDish() {
        return new DishDto.Builder().setName("").setDescription("delicious")
                .setWeight("150").setCost("180.5").setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                .build();
    }

    public static DishDto generateDishForUpdate() {
        return new DishDto.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                .setWeight("150").setCost("180.5").setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                .build();
    }

    public static List<Dish> generateDishesForSearch() {
        return new ArrayList<Dish>() {
            {
                add(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                        .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                        .build());
            }
        };
    }
}
