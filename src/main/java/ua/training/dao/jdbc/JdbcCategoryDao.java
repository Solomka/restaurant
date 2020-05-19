package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.CategoryDao;
import ua.training.entity.Category;
import ua.training.exception.ServerException;

public class JdbcCategoryDao implements CategoryDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcCategoryDao.class);

	private static String GET_ALL = "SELECT * FROM `category` ORDER BY name";
	private static String GET_BY_ID = "SELECT * FROM `category` WHERE id_category=?";
	private static String CREATE = "INSERT INTO `category` (name) VALUES (?)";
	private static String UPDATE = "UPDATE `category` SET name=? WHERE id_category=?";
	private static String DELETE = "DELETE FROM `category` WHERE id_category=?";
	private static String SEARCH_CATEGORY_BY_NAME = "SELECT * FROM `category` WHERE LOWER(name) LIKE CONCAT('%', LOWER(?), '%')";

	// table columns names
	private static String ID = "id_category";
	private static String NAME = "name";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcCategoryDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcCategoryDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Category> getAll() {
		List<Category> categories = new ArrayList<>();

		try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL)) {
			while (resultSet.next()) {
				categories.add(extractCategoryFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao getAll SQL exception", e);
			throw new ServerException(e);
		}
		return categories;
	}

	@Override
	public Optional<Category> getById(Long id) {
		Optional<Category> category = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				category = Optional.of(extractCategoryFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return category;
	}

	@Override
	public void create(Category category) {
		try (PreparedStatement query = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, category.getName());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				category.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao create SQL exception", e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(Category category) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
			query.setString(1, category.getName());
			query.setLong(2, category.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao update SQL exception: " + category.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	@Override
	public List<Category> searchCategoriesByName(String categoryName) {
		List<Category> categories = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_CATEGORY_BY_NAME)) {
			query.setString(1, categoryName);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				categories.add(extractCategoryFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao searchCategoryByName SQL exception: " + categoryName, e);
			throw new ServerException(e);
		}
		return categories;
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

	protected static Category extractCategoryFromResultSet(ResultSet resultSet) throws SQLException {
		return new Category.Builder().setId(resultSet.getLong(ID)).setName(resultSet.getString(NAME)).build();
	}

}
