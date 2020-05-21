package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.DishDao;
import ua.training.entity.Dish;
import ua.training.exception.ServerException;

public class JdbcDishDao implements DishDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcDishDao.class);

	private static String GET_ALL = "SELECT * FROM dish JOIN category USING(id_category) ORDER BY category.name";
	private static String GET_BY_ID = "SELECT * FROM dish JOIN category USING(id_category) WHERE id_dish=?";
	private static String CREATE = "INSERT INTO dish (name, description, weight, cost, id_category) VALUES (?, ?, ?, ?, ?)";
	private static String UPDATE = "UPDATE dish SET name=?, description=?, weight=?, cost=?, id_category=? WHERE id_dish=?";
	private static String DELETE = "DELETE FROM dish WHERE id_dish=?";
	private static String SEARCH_DISH_BY_NAME = "SELECT * FROM dish JOIN category USING(id_category) WHERE LOWER(dish.name) LIKE CONCAT('%', LOWER(?), '%')";
	private static String SEARCH_DISH_BY_CATEGORY_NAME = "SELECT * FROM dish JOIN category USING(id_category) WHERE category.name=?";
	private static String SEARCH_MOST_POPULAR_DISHES_IN_PERIOD = 
			"SELECT *"
					+ " FROM dish JOIN category USING(id_category)" 
					+ " WHERE id_dish IN (SELECT order_item.id_dish"
										+ " FROM `order` JOIN order_item USING(id_order)"
										+ " WHERE `date` BETWEEN ? AND ?"
										+ " GROUP BY id_dish" 
										+ "	HAVING COUNT(order_item.id_order)=(SELECT MAX(orders_number)"
																				+ "	FROM (SELECT COUNT(order_item.id_order)AS orders_number"
																				+ "	FROM `order` JOIN order_item USING(id_order)"
																				+ " WHERE `date` BETWEEN ? AND ?"
																				+ "	GROUP BY id_dish )AS `orders_counter`))";
	
	// table columns names
	private static String ID = "id_dish";
	private static String NAME = "dish.name";
	private static String DESCRIPTION = "description";
	private static String WEIGHT = "weight";
	private static String COST = "cost";
	private static String ID_CATEGORY = "id_category";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcDishDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcDishDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Dish> getAll() {
		List<Dish> dishes = new ArrayList<>();

		try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL)) {
			while (resultSet.next()) {
				dishes.add(extractDishFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao getAll SQL exception", e);
			throw new ServerException(e);
		}
		return dishes;
	}

	@Override
	public Optional<Dish> getById(Long id) {
		Optional<Dish> dish = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				dish = Optional.of(extractDishFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return dish;
	}

	@Override
	public void create(Dish dish) {
		try (PreparedStatement query = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, dish.getName());
			query.setString(2, dish.getDescription());
			query.setDouble(3, dish.getWeight());
			query.setBigDecimal(4, dish.getCost());
			query.setLong(5, dish.getCategory().getId());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				dish.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao create SQL exception", e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(Dish dish) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
			query.setString(1, dish.getName());
			query.setString(2, dish.getDescription());
			query.setDouble(3, dish.getWeight());
			query.setBigDecimal(4, dish.getCost());
			query.setLong(5, dish.getCategory().getId());
			query.setLong(6, dish.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao update SQL exception: " + dish.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}

	}

	@Override
	public List<Dish> searchDishByName(String name) {
		List<Dish> dishes = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_DISH_BY_NAME)) {
			query.setString(1, name);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				dishes.add(extractDishFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao searchDishByName SQL exception: " + name, e);
			throw new ServerException(e);
		}
		return dishes;
	}

	@Override
	public List<Dish> searchDishByCategoryName(String categoryName) {
		List<Dish> dishes = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_DISH_BY_CATEGORY_NAME)) {
			query.setString(1, categoryName);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				dishes.add(extractDishFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao searchDishByCategoryName SQL exception: " + categoryName, e);
			throw new ServerException(e);
		}
		return dishes;
	}

	@Override
	public List<Dish> searchMostPopularDishesPerPeriod(LocalDate fromDate, LocalDate toDate) {
		List<Dish> dishes = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_MOST_POPULAR_DISHES_IN_PERIOD)) {
			query.setDate(1, Date.valueOf(fromDate));
			query.setDate(2, Date.valueOf(toDate));
			query.setDate(3, Date.valueOf(fromDate));
			query.setDate(4, Date.valueOf(toDate));
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				dishes.add(extractDishFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcDishDao searchMostPopularDishesInPeriod SQL exception", e);
			throw new ServerException(e);
		}
		return dishes;
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcCategoryDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}
	
	private Dish extractDishFromResultSet(ResultSet resultSet) throws SQLException {
		return new Dish.Builder().setId(resultSet.getLong(ID)).setName(resultSet.getString(NAME))
				.setDescriprion(resultSet.getString(DESCRIPTION)).setWeight(resultSet.getDouble(WEIGHT))
				.setCost(resultSet.getBigDecimal(COST)).setCategory(JdbcCategoryDao.extractCategoryFromResultSet(resultSet))
				.build();
	}

}
