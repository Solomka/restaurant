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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetUpdateCategoryCommandTest {
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private CategoryService categoryService;

    private GetUpdateCategoryCommand getUpdateCategoryCommand;

    @Test
    public void shouldReturnUpdateCategoryViewOnExecute() throws ServletException, IOException {
        when(httpServletRequest.getParameter(Attribute.ID_CATEGORY)).thenReturn("1");
        Long categoryId = 1L;
        Optional<Category> category = CategoryTestDataGenerator.generateOptionalCategory();
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        String expectedResult = Page.ADD_UPDATE_CATEGORY_VIEW;
        getUpdateCategoryCommand = new GetUpdateCategoryCommand(categoryService);

        String actualResult = getUpdateCategoryCommand.execute(httpServletRequest, httpServletResponse);

        verify(categoryService).getCategoryById(categoryId);
        verify(httpServletRequest).setAttribute(Attribute.CATEGORY, category.get());
        assertEquals(expectedResult, actualResult);
    }
}
