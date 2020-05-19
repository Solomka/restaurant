package ua.training.dao;

import java.time.LocalDate;
import java.util.List;

import ua.training.entity.Dish;

public interface DishDao extends GenericDao<Dish, Long>, AutoCloseable {

	List<Dish> searchDishByName(String name);

	List<Dish> searchDishByCategoryName(String categoryName);

	List<Dish> searchMostPopularDishesPerPeriod(LocalDate fromDate, LocalDate toDate);

	void close();
}
