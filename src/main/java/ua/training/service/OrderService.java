package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.OrderDao;
import ua.training.entity.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    static final String GET_ALL_ORDERS = "Get all orders";
    static final String GET_ORDER_BY_ID = "Get order by id: %d";
    static final String CREATE_ORDER_WITH_DISHES = "Create order with dishes: %s";
    static final String UPDATE_ORDER = "Update order: %d";
    static final String DELETE_ORDER = "Delete order: %d";
    static final String SEARCH_WAITER_ORDERS = "Search waiter %d orders for %s";
    static final String SEARCH_UNPREPARED_ORDERS = "Search Unprepared orders for %s";
    static final String SEARCH_ORDERS_PER_PERIOD = "Search orders per period from  %s to %s";

    private final DaoFactory daoFactory;

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
        LOGGER.info(GET_ALL_ORDERS);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.getAll();
        }
    }

    public Optional<Order> getOrderById(Long orderId) {
        LOGGER.info(String.format(GET_ORDER_BY_ID, orderId));
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.getById(orderId);
        }
    }

    public void createOrder(Order order) {
        LOGGER.info(String.format(CREATE_ORDER_WITH_DISHES, order.getDate().toString()));
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            orderDao.create(order);
            orderDao.saveOrderDishes(order);
            connection.commit();
        }
    }

    public void updateOrder(Order order) {
        LOGGER.info(String.format(UPDATE_ORDER, order.getId()));
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.update(order);
        }
    }

    public void deleteOrder(Long orderId) {
        LOGGER.info(String.format(DELETE_ORDER, orderId));
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.delete(orderId);
        }
    }

    public List<Order> searchWaiterOrdersPerToday(Long idWaiter, LocalDate date) {
        LOGGER.info(String.format(SEARCH_WAITER_ORDERS, idWaiter, date.toString()));
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.searchWaiterOrdersForToday(idWaiter, date);
        }
    }

    public List<Order> searchUnpreparedOrdersForToday(LocalDate date) {
        LOGGER.info(String.format(SEARCH_UNPREPARED_ORDERS, date.toString()));
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.searchUpreparedOrdersForToday(date);
        }
    }

    public List<Order> searchOrdersByDate(LocalDate fromDate, LocalDate toDate) {
        LOGGER.info(String.format(SEARCH_ORDERS_PER_PERIOD, fromDate.toString(), toDate.toString()));
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.searchOrdersByDate(fromDate, toDate);
        }
    }
}
