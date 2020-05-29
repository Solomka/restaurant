package ua.training.testData;

import ua.training.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CategoryTestDataGenerator {

    private CategoryTestDataGenerator() {

    }

    public static List<Category> generateCategoryList() {
        return new ArrayList<Category>() {
            {
                add(new Category.Builder().setId(1L).setName("meat").build());
                add(new Category.Builder().setId(2L).setName("dessert").build());
            }
        };
    }

    public static Optional<Category> generateOptionalCategory() {
        return Optional.of(new Category.Builder().setId(1L).setName("meat").build());
    }

    public static Category generateCategoryForCreation() {
        return new Category.Builder().setName("meat").build();
    }

    public static Category generateCategoryForUpdate() {
        return new Category.Builder().setId(1L).setName("meat").build();
    }

    public static List<Category> generateCategoryForSearch() {
        return new ArrayList<Category>() {
            {
                add(new Category.Builder().setId(1L).setName("meat").build());
            }
        };
    }
}
