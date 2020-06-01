package ua.training.controller.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.auth.GetLoginCommand;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.UserService;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllUsersCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private UserService userService;

    private AllUsersCommand allUsersCommand;

    @Test
    public void shouldReturnLoginViewOnExecute() throws ServletException, IOException {
        List<User> users = UserTestDataGenerator.generateUsersList();
        when(userService.getAllUsers()).thenReturn(users);
        String expectedResult = Page.ALL_USERS_VIEW;
        allUsersCommand = new AllUsersCommand(userService);

        String actualResult = allUsersCommand.execute(httpServletRequest, httpServletResponse);

        assertEquals(expectedResult, actualResult);
        verify(httpServletRequest).setAttribute(Attribute.USERS, users);
        verify(httpServletRequest).setAttribute(Attribute.ROLES, Role.values());
    }
}
