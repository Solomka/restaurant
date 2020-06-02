package ua.training.controller.command.category;

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
public class GetAddCategoryCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    private GetAddCategoryCommand getAddCategoryCommand;

    @Test
    public void shouldReturnAddCategoryViewViewOnExecute() throws ServletException, IOException {
        String expectedResult = Page.ADD_UPDATE_CATEGORY_VIEW;
        getAddCategoryCommand = new GetAddCategoryCommand();

        String actualResult = getAddCategoryCommand.execute(httpServletRequest, httpServletResponse);

        assertEquals(expectedResult, actualResult);
    }
}
