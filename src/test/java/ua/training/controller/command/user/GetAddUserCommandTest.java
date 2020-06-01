package ua.training.controller.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.entity.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetAddUserCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    private GetAddUserCommand getAddUserCommand;

    @Test
    public void shouldReturnLoginViewOnExecute() throws ServletException, IOException {
        String expectedResult = Page.ADD_UPDATE_USER_VIEW;
        getAddUserCommand = new GetAddUserCommand();

        String actualResult = getAddUserCommand.execute(httpServletRequest, httpServletResponse);

        verify(httpServletRequest).setAttribute(Attribute.ROLES, Role.values());
        assertEquals(expectedResult, actualResult);
    }
}
