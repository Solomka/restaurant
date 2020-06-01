package ua.training.controller.command.user;

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
import ua.training.controller.command.auth.LogoutCommand;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.locale.Message;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectionManager.class)
public class DeleteUserCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Mock
    private UserService userService;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;

    private DeleteUserCommand deleteUserCommand;

    @Test
    public void shouldDeleteUserOnExecute() throws Exception {
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        Long userId = 1L;
        when(httpServletRequest.getParameter(Attribute.ID_USER)).thenReturn("1");
        doNothing().when(userService).deleteUser(userId);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(Attribute.SUCCESS, Message.SUCCESS_USER_DELETE);
        String expectedResult = RedirectionManager.REDIRECTION;
        deleteUserCommand = new DeleteUserCommand(userService);

        String actualResult = deleteUserCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService).deleteUser(userId);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_USERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
