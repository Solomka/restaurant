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
import ua.training.dto.DishDto;
import ua.training.entity.Category;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
import ua.training.testData.CategoryTestDataGenerator;
import ua.training.testData.DishTestDataGenerator;
import ua.training.validator.entity.DishDtoValidator;

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
@PrepareForTest({RedirectionManager.class, DishDtoValidator.class})
public class PostAddDishCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Mock
    private DishDtoValidator dishDtoValidator;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Captor
    private ArgumentCaptor<DishDto> dishDtoArgumentCaptor;
    @Mock
    private DishService dishService;
    @Mock
    private CategoryService categoryService;

    private PostAddDishCommand postAddDishCommand;

    @Test
    public void shouldAddDishWhenValidInputOnExecute() throws ServletException, IOException {
        DishDto dishDto = DishTestDataGenerator.generateDishForCreation();
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("cheesecake");
        when(httpServletRequest.getParameter(Attribute.DESCRIPTION)).thenReturn("delicious");
        when(httpServletRequest.getParameter(Attribute.WEIGHT)).thenReturn("150");
        when(httpServletRequest.getParameter(Attribute.COST)).thenReturn("180.5");
        when(httpServletRequest.getParameter(Attribute.CATEGORY)).thenReturn("1");
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        PowerMockito.mockStatic(DishDtoValidator.class);
        PowerMockito.when(DishDtoValidator.getInstance()).thenReturn(dishDtoValidator);
        List<String> errors = new ArrayList<>();
        when(dishDtoValidator.validate(dishDto)).thenReturn(errors);
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.SUCCESS, Message.SUCCESS_DISH_ADDITION);
            }
        };
        doNothing().when(dishService).createDish(dishDto);
        String expectedResult = RedirectionManager.REDIRECTION;
        postAddDishCommand = new PostAddDishCommand(dishService, categoryService);

        String actualResult = postAddDishCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).createDish(dishDto);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_DISHES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotAddDishWhenInvalidInputOnExecute() throws ServletException, IOException {
        List<Category> categories = CategoryTestDataGenerator.generateCategoryList();
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("");
        when(httpServletRequest.getParameter(Attribute.DESCRIPTION)).thenReturn("delicious");
        when(httpServletRequest.getParameter(Attribute.WEIGHT)).thenReturn("150");
        when(httpServletRequest.getParameter(Attribute.COST)).thenReturn("180.5");
        when(httpServletRequest.getParameter(Attribute.CATEGORY)).thenReturn("1");
        when(categoryService.getAllCategories()).thenReturn(categories);
        PowerMockito.mockStatic(DishDtoValidator.class);
        PowerMockito.when(DishDtoValidator.getInstance()).thenReturn(dishDtoValidator);
        List<String> errors = new ArrayList<String>() {
            {
                add(Message.INVALID_NAME_INPUT);
            }
        };
        when(dishDtoValidator.validate(dishDtoArgumentCaptor.capture())).thenReturn(errors);
        String expectedResult = Page.ADD_UPDATE_DISH_VIEW;
        postAddDishCommand = new PostAddDishCommand(dishService, categoryService);

        String actualResult = postAddDishCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService, never()).createDish(dishDtoArgumentCaptor.capture());
        verify(categoryService).getAllCategories();
        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        verify(httpServletRequest).setAttribute(eq(Attribute.DISH), dishDtoArgumentCaptor.capture());
        verify(httpServletRequest).setAttribute(Attribute.ERRORS, errors);
        assertEquals(expectedResult, actualResult);
    }
}
