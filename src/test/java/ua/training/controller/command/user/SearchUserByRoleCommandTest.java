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
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectionManager.class)
public class SearchUserByRoleCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Captor
    private ArgumentCaptor<Role> roleArgumentCaptor;
    @Mock
    private UserService userService;

    private SearchUserByRoleCommand searchUserByRoleCommand;

    @Test
    public void shouldFindUsersWhenValidInputOnExecute() throws ServletException, IOException {
        List<User> users = UserTestDataGenerator.generateUserForSearch();
        String role = "waiter";
        when(httpServletRequest.getParameter(Attribute.ROLE)).thenReturn(role);
        when(userService.searchUsersByRole(Role.forValue(role))).thenReturn(users);
        String expectedResult = Page.ALL_USERS_VIEW;
        searchUserByRoleCommand = new SearchUserByRoleCommand(userService);

        String actualResult = searchUserByRoleCommand.execute(httpServletRequest, httpServletResponse);

        verify(httpServletRequest).setAttribute(Attribute.USERS, users);
        verify(httpServletRequest).setAttribute(Attribute.ROLES, Role.values());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindUsersWhenInvalidInputOnExecute() throws ServletException, IOException {
        String role = "";
        when(httpServletRequest.getParameter(Attribute.ROLE)).thenReturn(role);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.INVALID_ROLE);
            }
        };
        searchUserByRoleCommand = new SearchUserByRoleCommand(userService);

        String actualResult = searchUserByRoleCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService, never()).searchUsersByRole(roleArgumentCaptor.capture());
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_USERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindUsersWhenValidInputUserNotExistOnExecute() throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        String role = "waiter";

        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(httpServletRequest.getParameter(Attribute.ROLE)).thenReturn(role);
        when(userService.searchUsersByRole(Role.forValue(role))).thenReturn(users);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.USER_IS_NOT_FOUND);
            }
        };
        searchUserByRoleCommand = new SearchUserByRoleCommand(userService);

        String actualResult = searchUserByRoleCommand.execute(httpServletRequest, httpServletResponse);;

        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_USERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
