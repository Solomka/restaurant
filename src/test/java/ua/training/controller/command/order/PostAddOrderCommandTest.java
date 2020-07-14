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
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.*;
import ua.training.locale.Message;
import ua.training.service.DishService;
import ua.training.service.OrderService;
import ua.training.testData.DishTestDataGenerator;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RedirectionManager.class, SessionManager.class, LocalDateTime.class})
public class PostAddOrderCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private HttpSession httpSession;
    @Mock
    private OrderService orderService;
    @Mock
    private DishService dishService;

    private PostAddOrderCommand postAddOrderCommand;

    @Test
    public void shouldAddOrderWhenValidInputOnExecute() throws ServletException, IOException {
        Optional<Dish> dish = DishTestDataGenerator.generateOptionalDish();
        User user = UserTestDataGenerator.generateUser();
        when(httpServletRequest.getParameterValues(Attribute.DISHES)).thenReturn(new String[]{"1"});
        when(dishService.getDishById(1L)).thenReturn(dish);
        PowerMockito.mockStatic(SessionManager.class);
        PowerMockito.when(SessionManager.getInstance()).thenReturn(sessionManager);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(sessionManager.getUserFromSession(httpSession)).thenReturn(user);
        LocalDateTime dateTime = LocalDateTime.of(2020, Month.MAY, 2, 3, 30, 3);
        PowerMockito.mockStatic(LocalDateTime.class);
        PowerMockito.when(LocalDateTime.now()).thenReturn(dateTime);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        doNothing().when(orderService).createOrder(orderArgumentCaptor.capture());
        Map<String, String> urlParams = new HashMap<String, String>(){
            {
                put(Attribute.SUCCESS, Message.SUCCESS_ORDER_ADDITION);
            }
        };
        String expectedResult = RedirectionManager.REDIRECTION;
        postAddOrderCommand = new PostAddOrderCommand(orderService, dishService);

        String actualResult = postAddOrderCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService).createOrder(orderArgumentCaptor.capture());
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_ORDERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotAddOrderWhenValidInputOnExecute() throws ServletException, IOException {
        when(httpServletRequest.getParameterValues(Attribute.DISHES)).thenReturn(null);
        List<Dish> dishes = DishTestDataGenerator.generateDishesList();
        when(dishService.getAllDishes()).thenReturn(dishes);
        List<String> errors = Collections.singletonList(Message.INVALID_DISHES);
        String expectedResult = Page.ADD_UPDATE_ORDER_VIEW;
        postAddOrderCommand = new PostAddOrderCommand(orderService, dishService);

        String actualResult = postAddOrderCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService, never()).createOrder(orderArgumentCaptor.capture());
        verify(httpServletRequest).setAttribute(Attribute.ERRORS, errors);
        verify(httpServletRequest).setAttribute(Attribute.DISHES, dishes);
        
        assertEquals(expectedResult, actualResult);
    }
}
