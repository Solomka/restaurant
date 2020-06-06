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
import ua.training.entity.Order;
import ua.training.locale.Message;
import ua.training.service.OrderService;
import ua.training.testData.OrderTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectionManager.class)
public class SearchOrdersPerPeriodTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Captor
    private ArgumentCaptor<LocalDate> localDateArgumentCaptor;
    @Mock
    private OrderService orderService;

    private SearchOrdersPerPeriod searchOrdersPerPeriod;

    @Test
    public void shouldFindOrdersWhenValidInputOnExecute() throws ServletException, IOException {
        List<Order> orders = OrderTestDataGenerator.generateOrdersList();
        String fromDateStr = "2020-05-01";
        String toDateStr = "2020-05-29";
        LocalDate fromDate = LocalDate.parse(fromDateStr);
        LocalDate toDate = LocalDate.parse(toDateStr);
        when(httpServletRequest.getParameter(Attribute.FROM_DATE)).thenReturn(fromDateStr);
        when(httpServletRequest.getParameter(Attribute.TO_DATE)).thenReturn(toDateStr);
        when(orderService.searchOrdersByDate(fromDate, toDate)).thenReturn(orders);
        String expectedResult = Page.ALL_ORDERS_VIEW;
        searchOrdersPerPeriod = new SearchOrdersPerPeriod(orderService);

        String actualResult = searchOrdersPerPeriod.execute(httpServletRequest, httpServletResponse);

        verify(orderService).searchOrdersByDate(fromDate, toDate);
        verify(httpServletRequest).setAttribute(Attribute.ORDERS, orders);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindOrdersWhenInvalidInputOnExecute() throws ServletException, IOException {
        String fromDateStr = "";
        String toDateStr = "";
        when(httpServletRequest.getParameter(Attribute.FROM_DATE)).thenReturn(fromDateStr);
        when(httpServletRequest.getParameter(Attribute.TO_DATE)).thenReturn(toDateStr);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.INVALID_DATE);
            }
        };
        searchOrdersPerPeriod = new SearchOrdersPerPeriod(orderService);

        String actualResult = searchOrdersPerPeriod.execute(httpServletRequest, httpServletResponse);

        verify(orderService, never()).searchOrdersByDate(localDateArgumentCaptor.capture(), localDateArgumentCaptor.capture());
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_ORDERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindOrdersWhenValidInputUserNotExistOnExecute() throws ServletException, IOException {
        List<Order> orders = new ArrayList<>();
        String fromDateStr = "2020-05-01";
        String toDateStr = "2020-05-29";
        LocalDate fromDate = LocalDate.parse(fromDateStr);
        LocalDate toDate = LocalDate.parse(toDateStr);
        when(httpServletRequest.getParameter(Attribute.FROM_DATE)).thenReturn(fromDateStr);
        when(httpServletRequest.getParameter(Attribute.TO_DATE)).thenReturn(toDateStr);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(orderService.searchOrdersByDate(fromDate, toDate)).thenReturn(orders);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.ORDER_IS_NOT_FOUND);
            }
        };
        searchOrdersPerPeriod = new SearchOrdersPerPeriod(orderService);

        String actualResult = searchOrdersPerPeriod.execute(httpServletRequest, httpServletResponse);

        verify(orderService).searchOrdersByDate(fromDate, toDate);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_ORDERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
