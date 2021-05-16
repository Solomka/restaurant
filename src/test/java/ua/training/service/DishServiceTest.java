package ua.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.training.service.DishService.CREATE_DISH;
import static ua.training.service.DishService.DELETE_DISH;
import static ua.training.service.DishService.GET_ALL_DISHES;
import static ua.training.service.DishService.GET_DISH_BY_ID;
import static ua.training.service.DishService.SEARCH_DISHES_BY_CATEGORY_NAME;
import static ua.training.service.DishService.SEARCH_DISHES_BY_NAME;
import static ua.training.service.DishService.SEARCH_MOST_POPULAR_DISH_PER_PERIOD;
import static ua.training.service.DishService.UPDATE_DISH;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.training.converter.DishDtoDishConverter;
import ua.training.dao.DaoFactory;
import ua.training.dao.DishDao;
import ua.training.dto.DishDto;
import ua.training.entity.Dish;
import ua.training.testData.DishTestDataGenerator;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LogManager.class, DaoFactory.class, DishService.class })
public class DishServiceTest {

	private static Logger LOGGER;

	@Mock
	private DaoFactory daoFactory;
	@Mock
	private DishDao dishDao;

	private DishService dishService;

	@BeforeClass
	public static void setUp() {
		LOGGER = PowerMockito.mock(Logger.class);
		PowerMockito.mockStatic(LogManager.class);
		PowerMockito.when(LogManager.getLogger(DishService.class)).thenReturn(LOGGER);
	}

	@Before
	public void setUpBeforeMethod() {
		dishService = new DishService(daoFactory);
		when(daoFactory.createDishDao()).thenReturn(dishDao);
	}

	// should ... when ... on
	// given ... when ... then
	@Test
	public void shouldReturnDishServiceInstanceOnGetInstance() throws Exception {
		PowerMockito.mockStatic(DaoFactory.class);
		PowerMockito.when(DaoFactory.getDaoFactory()).thenReturn(daoFactory);
		PowerMockito.whenNew(DishService.class).withArguments(daoFactory).thenReturn(dishService);

		DishService.getInstance();

		PowerMockito.verifyNew(DishService.class).withArguments(daoFactory);
	}

	@Test
	public void shouldReturnDishesOnGetAllDishes() {
		List<Dish> expectedResult = DishTestDataGenerator.generateDishesList();
		when(dishDao.getAll()).thenReturn(expectedResult);

		List<Dish> actualResult = dishService.getAllDishes();

		assertEquals(expectedResult, actualResult);
		verify(LOGGER, times(1)).info(GET_ALL_DISHES);
		verify(daoFactory).createDishDao();
		verify(dishDao).getAll();
	}

	@Test
	public void shouldReturnDishOnGetDishById() {
		Optional<Dish> expectedResult = DishTestDataGenerator.generateOptionalDish();
		Long dishId = 1L;
		when(dishDao.getById(dishId)).thenReturn(expectedResult);

		Optional<Dish> actualResult = dishService.getDishById(dishId);

		assertEquals(expectedResult.get(), actualResult.get());
		verify(LOGGER, times(1)).info(String.format(GET_DISH_BY_ID, dishId));
		verify(daoFactory).createDishDao();
		verify(dishDao).getById(dishId);
	}

	@Test
	public void shouldCreateDishOnCreateDish() {
		DishDto dishDto = DishTestDataGenerator.generateDishForCreation();

		dishService.createDish(dishDto);

		verify(LOGGER, times(1)).info(String.format(CREATE_DISH, dishDto.getName()));
		verify(daoFactory).createDishDao();
		verify(dishDao).create(DishDtoDishConverter.toDish(dishDto));
	}

	@Test
	public void shouldUpdateDishOnUpdateDish() {
		DishDto dishDto = DishTestDataGenerator.generateDishForUpdate();

		dishService.updateDish(dishDto);

		verify(LOGGER, times(1)).info(String.format(UPDATE_DISH, dishDto.getId()));
		verify(daoFactory).createDishDao();
		verify(dishDao).update(DishDtoDishConverter.toDish(dishDto));
	}

	@Test
	public void shouldDeleteDishOnDeleteDish() {
		Long dishId = 1L;

		dishService.deleteDish(dishId);

		verify(LOGGER, times(1)).info(String.format(DELETE_DISH, dishId));
		verify(daoFactory).createDishDao();
		verify(dishDao).delete(dishId);
	}

	@Test
	public void shouldSearchDishOnSearchDishesByName() {
		List<Dish> expectedResult = DishTestDataGenerator.generateDishesForSearch();
		String name = "cheesecake";
		when(dishDao.searchDishByName(name)).thenReturn(expectedResult);

		List<Dish> actualResult = dishService.searchDishesByName(name);

		assertEquals(expectedResult, actualResult);
		verify(LOGGER, times(1)).info(String.format(SEARCH_DISHES_BY_NAME, name));
		verify(daoFactory).createDishDao();
		verify(dishDao).searchDishByName(name);
	}

	@Test
	public void shouldSearchDishOnSearchDishesByCategoryName() {
		List<Dish> expectedResult = DishTestDataGenerator.generateDishesForSearch();
		String categoryName = "dessert";
		when(dishDao.searchDishByCategoryName(categoryName)).thenReturn(expectedResult);

		List<Dish> actualResult = dishService.searchDishesByCategoryName(categoryName);

		assertEquals(expectedResult, actualResult);
		verify(LOGGER, times(1)).info(String.format(SEARCH_DISHES_BY_CATEGORY_NAME, categoryName));
		verify(daoFactory).createDishDao();
		verify(dishDao).searchDishByCategoryName(categoryName);
	}

	@Test
	public void shouldSearchDishOnSearchMostPopularDishesPerPeriod() {
		List<Dish> expectedResult = DishTestDataGenerator.generateDishesForSearch();
		LocalDate fromDate = LocalDate.of(2020, Month.MAY, 1);
		LocalDate toDate = LocalDate.of(2020, Month.MAY, 29);
		when(dishDao.searchMostPopularDishesPerPeriod(fromDate, toDate)).thenReturn(expectedResult);

		List<Dish> actualResult = dishService.searchMostPopularDishesPerPeriod(fromDate, toDate);

		assertEquals(expectedResult, actualResult);
		verify(LOGGER, times(1))
				.info(String.format(SEARCH_MOST_POPULAR_DISH_PER_PERIOD, fromDate.toString(), toDate.toString()));
		verify(daoFactory).createDishDao();
		verify(dishDao).searchMostPopularDishesPerPeriod(fromDate, toDate);
	}
}
