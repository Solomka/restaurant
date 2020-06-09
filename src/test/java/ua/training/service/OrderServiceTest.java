package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.OrderDao;
import ua.training.entity.Order;
import ua.training.testData.OrderTestDataGenerator;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static ua.training.service.OrderService.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogManager.class, DaoFactory.class, OrderService.class})
public class OrderServiceTest {

    private static Logger LOGGER;

    @Mock
    private DaoFactory daoFactory;
    @Mock
    private DaoConnection daoConnection;
    @Mock
    private OrderDao orderDao;

    private OrderService orderService;

    @BeforeClass
    public static void setUp() {
        LOGGER = PowerMockito.mock(Logger.class);
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getLogger(OrderService.class)).thenReturn(LOGGER);
    }

    @Before
    public void setUpBeforeMethod() {
        orderService = new OrderService(daoFactory);
        when(daoFactory.getConnection()).thenReturn(daoConnection);
        when(daoFactory.createOrderDao(daoConnection)).thenReturn(orderDao);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
    }

    @Test
    public void shouldReturnOrderServiceInstanceOnGetInstance() throws Exception {
        PowerMockito.mockStatic(DaoFactory.class);
        PowerMockito.when(DaoFactory.getDaoFactory()).thenReturn(daoFactory);
        PowerMockito.whenNew(OrderService.class).withArguments(daoFactory).thenReturn(orderService);

        OrderService.getInstance();

        PowerMockito.verifyNew(OrderService.class).withArguments(daoFactory);
    }

    @Test
    public void shouldReturnOrdersOnGetAllOrders() {
        List<Order> expectedResult = OrderTestDataGenerator.generateOrdersList();
        when(orderDao.getAll()).thenReturn(expectedResult);

        List<Order> actualResult = orderService.getAllOrders();

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(GET_ALL_ORDERS);
        verify(daoFactory).createOrderDao();
        verify(orderDao).getAll();
    }

    @Test
    public void shouldReturnOrderOnGetOrderById() {
        Optional<Order> expectedResult = OrderTestDataGenerator.generateOptionalOrder();
        Long orderId = 1L;
        when(orderDao.getById(orderId)).thenReturn(expectedResult);

        Optional<Order> actualResult = orderService.getOrderById(orderId);

        assertEquals(expectedResult.get(), actualResult.get());
        verify(LOGGER, times(1)).info(String.format(GET_ORDER_BY_ID, orderId));
        verify(daoFactory).createOrderDao();
        verify(orderDao).getById(orderId);
    }

    @Test
    public void shouldCreateOrderOnCreateOrder() {
        Order order = OrderTestDataGenerator.generateOrderForCreation();

        orderService.createOrder(order);

        verify(LOGGER, times(1)).info(String.format(CREATE_ORDER_WITH_DISHES, order.getDate().toString()));
        verify(daoFactory).getConnection();
        verify(daoFactory).createOrderDao(daoConnection);
        verify(daoConnection).begin();
        verify(orderDao).create(order);
        verify(orderDao).saveOrderDishes(order);
        verify(daoConnection).commit();
    }

    @Test
    public void shouldUpdateDishOnUpdateDish() {
        Order order = OrderTestDataGenerator.generateOrderForUpdate();

        orderService.updateOrder(order);

        verify(LOGGER, times(1)).info(String.format(UPDATE_ORDER, order.getId()));
        verify(daoFactory).createOrderDao();
        verify(orderDao).update(order);
    }

    @Test
    public void shouldDeleteDishOnDeleteDish() {
        Long orderId = 1L;

        orderService.deleteOrder(orderId);

        verify(LOGGER, times(1)).info(String.format(DELETE_ORDER, orderId));
        verify(daoFactory).createOrderDao();
        verify(orderDao).delete(orderId);
    }

    @Test
    public void shouldSearchOrdersOnSearchWaiterOrdersPerToday() {
        List<Order> expectedResult = OrderTestDataGenerator.generateOrderForSearch();
        LocalDate date = LocalDate.of(2020, Month.MAY, 2);
        Long waiterId = 1L;
        when(orderDao.searchWaiterOrdersForToday(waiterId, date)).thenReturn(expectedResult);

        List<Order> actualResult = orderService.searchWaiterOrdersPerToday(waiterId, date);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_WAITER_ORDERS, waiterId, date.toString()));
        verify(daoFactory).createOrderDao();
        verify(orderDao).searchWaiterOrdersForToday(waiterId, date);
    }

    @Test
    public void shouldSearchOrdersOnSearchUnpreparedOrdersForToday() {
        List<Order> expectedResult = OrderTestDataGenerator.generateOrderForSearch();
        LocalDate date = LocalDate.of(2020, Month.MAY, 2);
        when(orderDao.searchUpreparedOrdersForToday(date)).thenReturn(expectedResult);

        List<Order> actualResult = orderService.searchUnpreparedOrdersForToday(date);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_UNPREPARED_ORDERS, date.toString()));
        verify(daoFactory).createOrderDao();
        verify(orderDao).searchUpreparedOrdersForToday(date);
    }

    @Test
    public void shouldSearchOrdersOnSearchOrdersByDate() {
        List<Order> expectedResult = OrderTestDataGenerator.generateOrderForSearch();
        LocalDate fromDate = LocalDate.of(2020, Month.MAY, 1);
        LocalDate toDate = LocalDate.of(2020, Month.MAY, 29);
        when(orderDao.searchOrdersByDate(fromDate, toDate)).thenReturn(expectedResult);

        List<Order> actualResult = orderService.searchOrdersByDate(fromDate, toDate);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_ORDERS_PER_PERIOD, fromDate.toString(), toDate.toString()));
        verify(daoFactory).createOrderDao();
        verify(orderDao).searchOrdersByDate(fromDate, toDate);
    }
}
