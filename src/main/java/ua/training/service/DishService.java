package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.converter.DishDtoDishConverter;
import ua.training.dao.DaoFactory;
import ua.training.dao.DishDao;
import ua.training.dto.DishDto;
import ua.training.entity.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DishService {

    private static final Logger LOGGER = LogManager.getLogger(DishService.class);
    static final String GET_ALL_DISHES = "Get all dishes";
    static final String GET_DISH_BY_ID = "Get dish by id: %d";
    static final String CREATE_DISH = "Create dish: %s";
    static final String UPDATE_DISH = "Update dish: %d";
    static final String DELETE_DISH = "Delete dish: %d";
    static final String SEARCH_DISHES_BY_NAME = "Search dishes by name: %s";
    static final String SEARCH_DISHES_BY_CATEGORY_NAME = "Search dishes by category name: %s";
    static final String SEARCH_MOST_POPULAR_DISH_PER_PERIOD = "Search most popular dish per period from %s to %s";

    private final DaoFactory daoFactory;

    DishService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class Holder {
        static final DishService INSTANCE = new DishService(DaoFactory.getDaoFactory());
    }

    public static DishService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Dish> getAllDishes() {
        LOGGER.info(GET_ALL_DISHES);
        try (DishDao dishDao = daoFactory.createDishDao()) {
            return dishDao.getAll();
        }
    }

    public Optional<Dish> getDishById(Long dishId) {
        LOGGER.info(String.format(GET_DISH_BY_ID, dishId));
        try (DishDao dishDao = daoFactory.createDishDao()) {
            return dishDao.getById(dishId);
        }
    }

    public void createDish(DishDto dishDto) {
        LOGGER.info(String.format(CREATE_DISH, dishDto.getName()));
        Dish dish = DishDtoDishConverter.toDish(dishDto);
        try (DishDao dishDao = daoFactory.createDishDao()) {
            dishDao.create(dish);
        }
    }

    public void updateDish(DishDto dishDto) {
        LOGGER.info(String.format(UPDATE_DISH, dishDto.getId()));
        Dish dish = DishDtoDishConverter.toDish(dishDto);
        try (DishDao dishDao = daoFactory.createDishDao()) {
            dishDao.update(dish);
        }
    }

    public void deleteDish(Long dishId) {
        LOGGER.info(String.format(DELETE_DISH, dishId));
        try (DishDao dishDao = daoFactory.createDishDao()) {
            dishDao.delete(dishId);
        }
    }

    public List<Dish> searchDishesByName(String dishName) {
        LOGGER.info(String.format(SEARCH_DISHES_BY_NAME, dishName));
        try (DishDao dishDao = daoFactory.createDishDao()) {
            return dishDao.searchDishByName(dishName);
        }
    }

    public List<Dish> searchDishesByCategoryName(String categoryName) {
        LOGGER.info(String.format(SEARCH_DISHES_BY_CATEGORY_NAME, categoryName));
        try (DishDao dishDao = daoFactory.createDishDao()) {
            return dishDao.searchDishByCategoryName(categoryName);
        }
    }

    public List<Dish> searchMostPopularDishesPerPeriod(LocalDate fromDate, LocalDate toDate) {
        LOGGER.info(String.format(SEARCH_MOST_POPULAR_DISH_PER_PERIOD, fromDate.toString(),
                toDate.toString()));
        try (DishDao dishDao = daoFactory.createDishDao()) {
            return dishDao.searchMostPopularDishesPerPeriod(fromDate, toDate);
        }
    }
}
