package ua.training.controller.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.UserService;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetUpdateUserCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private UserService userService;

    private GetUpdateUserCommand getUpdateUserCommand;

    @Test
    public void shouldReturnUpdateUserViewOnExecute() throws ServletException, IOException {
        when(httpServletRequest.getParameter(Attribute.ID_USER)).thenReturn("1");
        Long userId = 1L;
        Optional<User> user = UserTestDataGenerator.generateOptionalUser();
        when(userService.getUserById(userId)).thenReturn(user);
        String expectedResult = Page.ADD_UPDATE_USER_VIEW;
        getUpdateUserCommand = new GetUpdateUserCommand(userService);

        String actualResult = getUpdateUserCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService).getUserById(userId);
        verify(httpServletRequest).setAttribute(Attribute.USER_DTO, user.get());
        assertEquals(expectedResult, actualResult);
    }
}
