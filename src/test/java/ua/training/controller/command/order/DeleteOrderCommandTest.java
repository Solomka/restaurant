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
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectionManager.class)
public class DeleteOrderCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Mock
    private OrderService orderService;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;

    private DeleteOrderCommand deleteOrderCommand;

    @Test
    public void shouldDeleteOrderOnExecute() throws Exception {
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        Long orderId = 1L;
        when(httpServletRequest.getParameter(Attribute.ID_ORDER)).thenReturn("1");
        doNothing().when(orderService).deleteOrder(orderId);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(Attribute.SUCCESS, Message.SUCCESS_ORDER_DELETE);
        String expectedResult = RedirectionManager.REDIRECTION;
        deleteOrderCommand = new DeleteOrderCommand(orderService);

        String actualResult = deleteOrderCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService).deleteOrder(orderId);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_ORDERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
