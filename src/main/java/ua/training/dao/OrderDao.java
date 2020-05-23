package ua.training.dao;

import java.time.LocalDate;
import java.util.List;

import ua.training.entity.Order;

public interface OrderDao extends GenericDao<Order, Long>, AutoCloseable {

	void saveOrderDishes(Order order);

	List<Order> searchWaiterOrdersForToday(Long idWaiter, LocalDate date);

	List<Order> searchUpreparedOrdersForToday(LocalDate date);

	List<Order> searchOrdersByDate(LocalDate fromDate, LocalDate toDate);

	void close();
}
