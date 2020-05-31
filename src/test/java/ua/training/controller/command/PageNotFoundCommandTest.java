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
public class PageNotFoundCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    private PageNotFoundCommand pageNotFoundCommand;

    @Test
    public void shouldReturnLoginViewOnExecute() throws ServletException, IOException {
        String expectedResult = Page.PAGE_NOT_FOUND;
        pageNotFoundCommand = new PageNotFoundCommand();

        String actualResult = pageNotFoundCommand.execute(httpServletRequest, httpServletResponse);

        assertEquals(expectedResult, actualResult);
    }
}
