package ua.training.controller.command.dish;

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
import ua.training.controller.command.user.DeleteUserCommand;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectionManager.class)
public class DeleteDishCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Mock
    private DishService dishService;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;

    private DeleteDishCommand deleteDishCommand;

    @Test
    public void shouldDeleteDishOnExecute() throws Exception {
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        Long dishId = 1L;
        when(httpServletRequest.getParameter(Attribute.ID_DISH)).thenReturn("1");
        doNothing().when(dishService).deleteDish(dishId);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(Attribute.SUCCESS, Message.SUCCESS_DISH_DELETE);
        String expectedResult = RedirectionManager.REDIRECTION;
        deleteDishCommand = new DeleteDishCommand(dishService);

        String actualResult = deleteDishCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).deleteDish(dishId);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_DISHES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
