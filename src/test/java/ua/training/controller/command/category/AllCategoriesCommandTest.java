package ua.training.controller.command.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.entity.Category;
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
public class AllCategoriesCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private CategoryService categoryService;

    private AllCategoriesCommand allCategoriesCommand;

    @Test
    public void shouldReturnAllCategoriesOnExecute() throws ServletException, IOException {
        List<Category> categories = CategoryTestDataGenerator.generateCategoryList();
        when(categoryService.getAllCategories()).thenReturn(categories);
        String expectedResult = Page.ALL_CATEGORIES_VIEW;
        allCategoriesCommand = new AllCategoriesCommand(categoryService);

        String actualResult = allCategoriesCommand.execute(httpServletRequest, httpServletResponse);

        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        assertEquals(expectedResult, actualResult);
    }
}
