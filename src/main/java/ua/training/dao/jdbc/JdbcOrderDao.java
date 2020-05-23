package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.OrderDao;
import ua.training.entity.Dish;
import ua.training.entity.Order;
import ua.training.entity.Status;
import ua.training.exception.ServerException;

public class JdbcOrderDao implements OrderDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcOrderDao.class);

	private static String GET_ALL = "SELECT id_order, `date`, `status`, total, id_user, user.name, surname, dish.name"
			+ " FROM `order` JOIN `user` USING(id_user) JOIN order_item USING(id_order) JOIN dish USING(id_dish)"
			+ " ORDER BY date DESC";
	private static String GET_BY_ID = "SELECT id_order, `date`, `status`, total, id_user, user.name, surname, dish.name"
			+ " FROM `order` JOIN `user` USING(id_user) JOIN order_item USING(id_order) JOIN dish USING(id_dish)"
			+ " WHERE id_order=?" 
			+ " ORDER BY date DESC";

	private static String CREATE = "INSERT INTO order (date, status, total, id_user) VALUES (?, ?, ?, ?)";
	private static String SAVE_ORDER_DISHES = "INSERT INTO order_item (id_order, id_dish) VALUES (?, ?)";

	private static String UPDATE = "UPDATE order SET status=? WHERE id_order=?";
	private static String DELETE = "DELETE FROM order WHERE id_order=?";

	private static String SEARCH_WAITER_ORDERS_PER_PERIOD = "SELECT id_order, `date`, `status`, total, id_user, user.name, surname, dish.name"
			+ " FROM `order` JOIN `user` USING(id_user) JOIN order_item USING(id_order) JOIN dish USING(id_dish)"
			+ " WHERE date=? AND id_user=?" 
			+ " ORDER BY date DESC";
	private static String SEARCH_UNPREPARED_ORDERS_PER_PERIOD = "SELECT id_order, `date`, `status`, total, id_user, user.name, surname, dish.name"
			+ " FROM `order` JOIN `user` USING(id_user) JOIN order_item USING(id_order) JOIN dish USING(id_dish)"
			+ " WHERE date=? AND (status='new' OR status='in progress')" 
			+ " ORDER BY date DESC, status";

	private static String SEARCH_ORDERS_PER_PERIOD = "SELECT id_order, `date`, `status`, total, id_user, user.name, surname, dish.name"
			+ " FROM `order` JOIN `user` USING(id_user) JOIN order_item USING(id_order) JOIN dish USING(id_dish)"
			+ " WHERE date BETWEEN ? AND ?" + " ORDER BY date DESC";

	// order table columns names
	private static String ID = "id_order";
	private static String DATE = "date";
	private static String STATUS = "status";
	private static String TOTAL = "total";
	private static String ID_USER = "id_user";

	// order_item table columns names
	private static String ID_ORDER = "order_item.id_order";
	private static String ID_DISH = "order_item.id_dish";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcOrderDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcOrderDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Order> getAll() {
		List<Order> orders = new ArrayList<>();

		try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL)) {
			while (resultSet.next()) {
				orders.add(extractOrderWithDishesFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao getAll SQL exception", e);
			throw new ServerException(e);
		}
		return orders;
	}

	@Override
	public Optional<Order> getById(Long id) {
		Optional<Order> order = Optional.empty();

		try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				order = Optional.of(extractOrderWithDishesFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return order;
	}

	@Override
	public void create(Order order) {
		try (PreparedStatement query = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			query.setTimestamp(1, Timestamp.valueOf(order.getDate()));
			query.setString(2, order.getStatus().getValue());
			query.setBigDecimal(3, order.getTotal());
			query.setLong(4, order.getUser().getId());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();

			if (keys.next()) {
				order.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao create SQL exception: " + order.getDate(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void saveOrderDishes(Order order) {
		List<Dish> dishes = order.getDishes();

		try (PreparedStatement query = connection.prepareStatement(SAVE_ORDER_DISHES)) {
			for (Dish dish : dishes) {
				query.setLong(1, order.getId());
				query.setLong(2, dish.getId());
				query.addBatch();
			}
			query.executeBatch();

		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao saveOrderDishes SQL exception: " + order.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(Order order) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
			query.setString(1, order.getStatus().getValue());
			query.setLong(2, order.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao update SQL exception: " + order.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	@Override
	public List<Order> searchWaiterOrdersForToday(Long idWaiter, LocalDate date) {
		List<Order> orders = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_WAITER_ORDERS_PER_PERIOD)) {
			query.setDate(1, Date.valueOf(date));
			query.setLong(2, idWaiter);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				orders.add(extractOrderWithDishesFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao searchWaiterOrdersPerToday SQL exception", e);
			throw new ServerException(e);
		}
		return orders;
	}

	@Override
	public List<Order> searchUpreparedOrdersForToday(LocalDate date) {
		List<Order> orders = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_UNPREPARED_ORDERS_PER_PERIOD)) {
			query.setDate(1, Date.valueOf(date));
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				orders.add(extractOrderWithDishesFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao searchUpreparedOrdersPerToday SQL exception", e);
			throw new ServerException(e);
		}
		return orders;
	}

	@Override
	public List<Order> searchOrdersByDate(LocalDate fromDate, LocalDate toDate) {
		List<Order> orders = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_ORDERS_PER_PERIOD)) {
			query.setDate(1, Date.valueOf(fromDate));
			query.setDate(1, Date.valueOf(toDate));
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				orders.add(extractOrderWithDishesFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao searchOrdersByDate SQL exception", e);
			throw new ServerException(e);
		}
		return orders;
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcOrderDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}

	protected static Order extractOrderWithDishesFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = extractOrderFromResultSet(resultSet);

		while (resultSet.next() && order.equals(extractOrderFromResultSet(resultSet))) {
			order.addDish(JdbcDishDao.extractDishFromResultSet(resultSet));
		}
		resultSet.previous();
		return order;
	}

	protected static Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
		return new Order.Builder().setId(resultSet.getLong(ID)).setDate(resultSet.getTimestamp(DATE).toLocalDateTime())
				.setStatus(Status.forValue(resultSet.getString(STATUS))).setTotal(resultSet.getBigDecimal(TOTAL))
				.setUser(JdbcUserDao.extractUserGeneralInfoFromResultSet(resultSet)).build();
	}
}
