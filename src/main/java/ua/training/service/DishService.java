package ua.training.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.DaoFactory;
import ua.training.dao.DishDao;
import ua.training.entity.Dish;

public class DishService {

	private static final Logger LOGGER = LogManager.getLogger(DishService.class);

	private DaoFactory daoFactory;

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
		LOGGER.info("Get all dishes");
		try (DishDao dishDao = daoFactory.createDishDao()) {
			return dishDao.getAll();
		}
	}

	public Optional<Dish> getDishById(Long dishId) {
		LOGGER.info("Get dish by id: " + dishId);
		try (DishDao dishDao = daoFactory.createDishDao()) {
			return dishDao.getById(dishId);
		}
	}

	public void createDish(Dish dish) {
		LOGGER.info("Create dish: " + dish.getName());
		try (DishDao dishDao = daoFactory.createDishDao()) {
			dishDao.create(dish);
		}
	}

	public void updateDish(Dish dish) {
		LOGGER.info("Update dish: " + dish.getName());
		try (DishDao dishDao = daoFactory.createDishDao()) {
			dishDao.update(dish);
		}
	}

	public void deleteDish(Long dishId) {
		LOGGER.info("Delete dish: " + dishId);
		try (DishDao dishDao = daoFactory.createDishDao()) {
			dishDao.delete(dishId);
		}
	}

	public List<Dish> searchDishesByName(String dishName) {
		LOGGER.info("Search dishes by name: " + dishName);
		try (DishDao dishDao = daoFactory.createDishDao()) {
			return dishDao.searchDishByName(dishName);
		}
	}

	public List<Dish> searchDishesByCategoryName(String categoryName) {
		LOGGER.info("Search dishes by category name: " + categoryName);
		try (DishDao dishDao = daoFactory.createDishDao()) {
			return dishDao.searchDishByName(categoryName);
		}
	}

	public List<Dish> searchMostPopularDishesPerPeriod(LocalDate fromDate, LocalDate toDate) {
		LOGGER.info(String.format("Search most populat dish per period from  %s to %s ", fromDate.toString(), toDate.toString()));
		try (DishDao dishDao = daoFactory.createDishDao()) {
			return dishDao.searchMostPopularDishesPerPeriod(fromDate, toDate);
		}
	}

}
