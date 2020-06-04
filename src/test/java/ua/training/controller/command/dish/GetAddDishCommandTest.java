package ua.training.controller.command.dish;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.user.GetAddUserCommand;
import ua.training.entity.Category;
import ua.training.entity.Role;
import ua.training.service.CategoryService;
import ua.training.testData.CategoryTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAddDishCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private CategoryService categoryService;

    private GetAddDishCommand getAddDishCommand;

    @Test
    public void shouldReturnAddDishViewViewOnExecute() throws ServletException, IOException {
        List<Category> categories = CategoryTestDataGenerator.generateCategoryList();
        when(categoryService.getAllCategories()).thenReturn(categories);
        String expectedResult = Page.ADD_UPDATE_DISH_VIEW;
        getAddDishCommand = new GetAddDishCommand(categoryService);

        String actualResult = getAddDishCommand.execute(httpServletRequest, httpServletResponse);

        verify(categoryService).getAllCategories();
        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        assertEquals(expectedResult, actualResult);
    }
}
