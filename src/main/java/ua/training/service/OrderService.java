package ua.training.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.OrderDao;
import ua.training.entity.Order;

public class OrderService {

	private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

	private DaoFactory daoFactory;

	OrderService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final OrderService INSTANCE = new OrderService(DaoFactory.getDaoFactory());
	}

	public static OrderService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Order> getAllOrders() {
		LOGGER.info("Get all orders");
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			return orderDao.getAll();
		}
	}

	public Optional<Order> getOrderById(Long orderId) {
		LOGGER.info("Get order by id: " + orderId);
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			return orderDao.getById(orderId);
		}
	}

	public void createOrder(Order order) {
		LOGGER.info("Create order with dishes: " + order.getDate());
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			OrderDao orderDao = daoFactory.createOrderDao(connection);
			orderDao.create(order);
			orderDao.saveOrderDishes(order);
			connection.commit();
		}
	}

	public void updateOrder(Order order) {
		LOGGER.info("Update order: " + order.getDate());
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			orderDao.update(order);
		}
	}

	public void deleteOrder(Long orderId) {
		LOGGER.info("Delete order: " + orderId);
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			orderDao.delete(orderId);
		}
	}

	public List<Order> searchWaiterOrdersPerToday(Long idWaiter, LocalDate date) {
		LOGGER.info(String.format("Search waiter %d orders for %s", idWaiter, date.toString()));
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			return orderDao.searchWaiterOrdersForToday(idWaiter, date);
		}
	}

	public List<Order> searchUpreparedOrdersForToday(LocalDate date) {
		LOGGER.info(String.format("Search Unprepared orders for %s", date.toString()));
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			return orderDao.searchUpreparedOrdersForToday(date);
		}
	}

	public List<Order> searchOrdersByDate(LocalDate fromDate, LocalDate toDate) {
		LOGGER.info(String.format("Search orders per period from  %s to %s ", fromDate.toString(), toDate.toString()));
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			return orderDao.searchOrdersByDate(fromDate, toDate);
		}
	}
}
