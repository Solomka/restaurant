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
import ua.training.entity.Order;
import ua.training.entity.Status;
import ua.training.locale.Message;
import ua.training.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectionManager.class)
public class PostUpdateOrderCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Mock
    private OrderService orderService;

    private PostUpdateOrderCommand postUpdateOrderCommand;

    @Test
    public void shouldUpdateOrderOnExecute() throws ServletException, IOException {
        Order order = new Order.Builder().setId(1L)
                .setStatus(Status.PREPARED).build();
        when(httpServletRequest.getParameter(Attribute.ID_ORDER)).thenReturn("1");
        when(httpServletRequest.getParameter(Attribute.STATUS)).thenReturn("prepared");
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(Attribute.SUCCESS, Message.SUCCESS_ORDER_UPDATE);
        String expectedResult = RedirectionManager.REDIRECTION;
        postUpdateOrderCommand = new PostUpdateOrderCommand(orderService);

        String actualResult = postUpdateOrderCommand.execute(httpServletRequest, httpServletResponse);

        verify(orderService).updateOrder(order);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_ORDERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
