package ua.training.controller.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HomeCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    private HomeCommand homeCommand;

    @Test
    public void shouldReturnLoginViewOnExecute() throws ServletException, IOException {
        String expectedResult = Page.HOME_VIEW;
        homeCommand = new HomeCommand();

        String actualResult = homeCommand.execute(httpServletRequest, httpServletResponse);

        assertEquals(expectedResult, actualResult);
    }
}
