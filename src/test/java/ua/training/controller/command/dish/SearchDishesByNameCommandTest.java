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
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RedirectionManager.class, FieldValidatorsChainGenerator.class})
public class SearchDishesByNameCommandTest {

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

    private SearchDishesByNameCommand searchDishesByNameCommand;

    @Test
    public void shouldFindDishesWhenValidInputOnExecute() throws ServletException, IOException {
        List<Dish> dishes = DishTestDataGenerator.generateDishesForSearch();
        List<Category> categories = CategoryTestDataGenerator.generateCategoryList();
        String name = "cheesecake";
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn(name);
        when(dishService.searchDishesByName(name)).thenReturn(dishes);
        when(categoryService.getAllCategories()).thenReturn(categories);
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(eq(FieldValidatorKey.NAME), eq(name), anyListOf(String.class));
        String expectedResult = Page.ALL_DISHES_VIEW;
        searchDishesByNameCommand = new SearchDishesByNameCommand(dishService, categoryService);

        String actualResult = searchDishesByNameCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).searchDishesByName(name);
        verify(categoryService).getAllCategories();
        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        verify(httpServletRequest).setAttribute(Attribute.DISHES, dishes);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindDishesWhenInvalidInputOnExecute() throws ServletException, IOException {
        String name = "";
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn(name);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        List<String> errors = new ArrayList<>();
        doAnswer(invocation -> {
            List<String> errorsList = invocation.getArgumentAt(2, List.class);
            errorsList.add(Message.INVALID_NAME);
            return null;
        }).when(abstractFieldValidatorHandler).validateField(FieldValidatorKey.NAME, name, errors);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.INVALID_NAME);
            }
        };
        searchDishesByNameCommand = new SearchDishesByNameCommand(dishService, categoryService);

        String actualResult = searchDishesByNameCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService, never()).searchDishesByName(name);
        verify(categoryService, never()).getAllCategories();
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_DISHES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindDishesWhenValidInputUserNotExistOnExecute() throws ServletException, IOException {
        List<Dish> dishes = new ArrayList<>();
        String name = "cheesecake";
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn(name);
        when(dishService.searchDishesByName(name)).thenReturn(dishes);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.DISH_IS_NOT_FOUND);
            }
        };
        searchDishesByNameCommand = new SearchDishesByNameCommand(dishService, categoryService);

        String actualResult = searchDishesByNameCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).searchDishesByName(name);
        verify(categoryService, never()).getAllCategories();
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_DISHES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
