package ua.training.controller.command.category;

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
import ua.training.entity.Category;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.testData.CategoryTestDataGenerator;
import ua.training.validator.entity.CategoryValidator;

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
@PrepareForTest({RedirectionManager.class, CategoryValidator.class})
public class PostUpdateCategoryCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Mock
    private CategoryValidator categoryValidator;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;
    @Mock
    private CategoryService categoryService;

    private PostUpdateCategoryCommand postUpdateCategoryCommand;

    @Test
    public void shouldUpdateCategoryWhenValidInputOnExecute() throws ServletException, IOException {
        Category category = CategoryTestDataGenerator.generateCategoryForUpdate();
        when(httpServletRequest.getParameter(Attribute.ID_CATEGORY)).thenReturn("1");
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("meat");
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        PowerMockito.mockStatic(CategoryValidator.class);
        PowerMockito.when(CategoryValidator.getInstance()).thenReturn(categoryValidator);
        List<String> errors = new ArrayList<>();
        when(categoryValidator.validate(category)).thenReturn(errors);
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.SUCCESS, Message.SUCCESS_CATEGORY_UPDATE);
            }
        };
        doNothing().when(categoryService).updateCategory(category);
        String expectedResult = RedirectionManager.REDIRECTION;
        postUpdateCategoryCommand = new PostUpdateCategoryCommand(categoryService);

        String actualResult = postUpdateCategoryCommand.execute(httpServletRequest, httpServletResponse);

        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_CATEGORIES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotAddCategoryWhenInvalidInputOnExecute() throws ServletException, IOException {
        when(httpServletRequest.getParameter(Attribute.ID_CATEGORY)).thenReturn("1");
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("");
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        PowerMockito.mockStatic(CategoryValidator.class);
        PowerMockito.when(CategoryValidator.getInstance()).thenReturn(categoryValidator);
        List<String> errors = new ArrayList<String>() {
            {
                add(Message.INVALID_NAME_INPUT);
            }
        };
        when(categoryValidator.validate(categoryArgumentCaptor.capture())).thenReturn(errors);

        String expectedResult = Page.ADD_UPDATE_CATEGORY_VIEW;
        postUpdateCategoryCommand = new PostUpdateCategoryCommand(categoryService);

        String actualResult = postUpdateCategoryCommand.execute(httpServletRequest, httpServletResponse);

        verify(categoryService, never()).updateCategory(categoryArgumentCaptor.capture());
        verify(httpServletRequest).setAttribute(eq(Attribute.CATEGORY), categoryArgumentCaptor.capture());
        verify(httpServletRequest).setAttribute(Attribute.ERRORS, errors);
        assertEquals(expectedResult, actualResult);
    }
}
