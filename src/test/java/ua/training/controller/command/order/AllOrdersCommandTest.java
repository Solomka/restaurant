package ua.training.controller.command.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.Order;
import ua.training.entity.User;
import ua.training.service.OrderService;
import ua.training.testData.OrderTestDataGenerator;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SessionManager.class)
public class AllOrdersCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private HttpSession httpSession;
    @Mock
    private OrderService orderService;
    @Captor
    private ArgumentCaptor<LocalDate> localDateArgumentCaptor;

    private AllOrdersCommand allOrdersCommand;

    @Test
    public void shouldReturnAllOrdersWithWaiterOrdersPerTodayWhenUserIsWaiterOnExecute() throws ServletException, IOException {
        User user = UserTestDataGenerator.generateUser();
        List<Order> orders = OrderTestDataGenerator.generateOrdersList();
        PowerMockito.mockStatic(SessionManager.class);
        PowerMockito.when(SessionManager.getInstance()).thenReturn(sessionManager);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(sessionManager.getUserFromSession(httpSession)).thenReturn(user);
        when(orderService.searchWaiterOrdersPerToday(eq(user.getId()), localDateArgumentCaptor.capture())).thenReturn(orders);
        String expectedResult = Page.ALL_ORDERS_VIEW;
        allOrdersCommand = new AllOrdersCommand(orderService);

        String actualResult = allOrdersCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService).searchWaiterOrdersPerToday(eq(user.getId()), localDateArgumentCaptor.capture());
        verify(httpServletRequest).setAttribute(Attribute.ORDERS, orders);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnAllOrdersWithUnpreparedOrdersForTodayWhenUserIsChiefOnExecute() throws ServletException, IOException {
        User user = UserTestDataGenerator.generateChiefUser();
        List<Order> orders = OrderTestDataGenerator.generateOrdersList();
        PowerMockito.mockStatic(SessionManager.class);
        PowerMockito.when(SessionManager.getInstance()).thenReturn(sessionManager);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(sessionManager.getUserFromSession(httpSession)).thenReturn(user);
        when(orderService.searchUnpreparedOrdersForToday(localDateArgumentCaptor.capture())).thenReturn(orders);
        String expectedResult = Page.ALL_ORDERS_VIEW;
        allOrdersCommand = new AllOrdersCommand(orderService);

        String actualResult = allOrdersCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService).searchUnpreparedOrdersForToday(localDateArgumentCaptor.capture());
        verify(httpServletRequest).setAttribute(Attribute.ORDERS, orders);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnAllOrdersWithAllOrdersWhenUserIsManagerOnExecute() throws ServletException, IOException {
        User user = UserTestDataGenerator.generateManagerUser();
        List<Order> orders = OrderTestDataGenerator.generateOrdersList();
        PowerMockito.mockStatic(SessionManager.class);
        PowerMockito.when(SessionManager.getInstance()).thenReturn(sessionManager);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(sessionManager.getUserFromSession(httpSession)).thenReturn(user);
        when(orderService.getAllOrders()).thenReturn(orders);
        String expectedResult = Page.ALL_ORDERS_VIEW;
        allOrdersCommand = new AllOrdersCommand(orderService);

        String actualResult = allOrdersCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService).getAllOrders();
        verify(httpServletRequest).setAttribute(Attribute.ORDERS, orders);
        assertEquals(expectedResult, actualResult);
    }
}
