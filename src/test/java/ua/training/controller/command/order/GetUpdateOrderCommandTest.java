package ua.training.controller.command.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.entity.Dish;
import ua.training.entity.Order;
import ua.training.entity.Status;
import ua.training.service.DishService;
import ua.training.service.OrderService;
import ua.training.testData.DishTestDataGenerator;
import ua.training.testData.OrderTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class GetUpdateOrderCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private OrderService orderService;
    @Mock
    private DishService dishService;
    @Captor
    private ArgumentCaptor<Status> statusArgumentCaptor;

    private GetUpdateOrderCommand getUpdateOrderCommand;

    @Test
    public void shouldReturnUpdateOrderViewOnExecute() throws ServletException, IOException {
        when(httpServletRequest.getParameter(Attribute.ID_ORDER)).thenReturn("1");
        Long orderId = 1L;
        Optional<Order> order = OrderTestDataGenerator.generateOptionalOrder();
        when(orderService.getOrderById(orderId)).thenReturn(order);
        List<Dish> dishes = DishTestDataGenerator.generateDishesList();
        when(dishService.getAllDishes()).thenReturn(dishes);
        String expectedResult = Page.ADD_UPDATE_ORDER_VIEW;
        getUpdateOrderCommand = new GetUpdateOrderCommand(orderService, dishService);

        String actualResult = getUpdateOrderCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService).getOrderById(orderId);
        verify(dishService).getAllDishes();
        verify(httpServletRequest).setAttribute(Attribute.ORDER, order.get());
        verify(httpServletRequest).setAttribute(Attribute.DISHES, dishes);
        verify(httpServletRequest).setAttribute(eq(Attribute.STATUSES), statusArgumentCaptor.capture());
        assertEquals(expectedResult, actualResult);
    }
}
