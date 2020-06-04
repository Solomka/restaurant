package ua.training.controller.command.dish;

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
import ua.training.entity.Dish;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
import ua.training.testData.CategoryTestDataGenerator;
import ua.training.testData.DishTestDataGenerator;
import ua.training.validator.field.AbstractFieldValidatorHandler;

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
@PrepareForTest(RedirectionManager.class)
public class SearchDishesByCategoryCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Mock
    private AbstractFieldValidatorHandler abstractFieldValidatorHandler;
    @Mock
    private DishService dishService;
    @Mock
    private CategoryService categoryService;

    private SearchDishesByCategoryCommand searchDishesByCategoryCommand;

    @Test
    public void shouldFindDishesWhenValidInputOnExecute() throws ServletException, IOException {
        List<Dish> dishes = DishTestDataGenerator.generateDishesForSearch();
        List<Category> categories = CategoryTestDataGenerator.generateCategoryList();
        String name = "dessert";
        when(httpServletRequest.getParameter(Attribute.CATEGORY)).thenReturn(name);
        when(dishService.searchDishesByCategoryName(name)).thenReturn(dishes);
        when(categoryService.getAllCategories()).thenReturn(categories);
        String expectedResult = Page.ALL_DISHES_VIEW;
        searchDishesByCategoryCommand = new SearchDishesByCategoryCommand(dishService, categoryService);

        String actualResult = searchDishesByCategoryCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).searchDishesByCategoryName(name);
        verify(categoryService).getAllCategories();
        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        verify(httpServletRequest).setAttribute(Attribute.DISHES, dishes);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindDishesWhenInvalidInputOnExecute() throws ServletException, IOException {
        String name = "";
        when(httpServletRequest.getParameter(Attribute.CATEGORY)).thenReturn(name);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.INVALID_CATEGORY);
            }
        };
        searchDishesByCategoryCommand = new SearchDishesByCategoryCommand(dishService, categoryService);

        String actualResult = searchDishesByCategoryCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService, never()).searchDishesByCategoryName(name);
        verify(categoryService, never()).getAllCategories();
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_DISHES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindDishesWhenValidInputUserNotExistOnExecute() throws ServletException, IOException {
        List<Dish> dishes = new ArrayList<>();
        String name = "dessert";
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(httpServletRequest.getParameter(Attribute.CATEGORY)).thenReturn(name);
        when(dishService.searchDishesByCategoryName(name)).thenReturn(dishes);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.DISH_IS_NOT_FOUND);
            }
        };
        searchDishesByCategoryCommand = new SearchDishesByCategoryCommand(dishService, categoryService);

        String actualResult = searchDishesByCategoryCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).searchDishesByCategoryName(name);
        verify(categoryService, never()).getAllCategories();
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_DISHES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
