package ua.training.controller.command.dish;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.user.AllUsersCommand;
import ua.training.entity.Category;
import ua.training.entity.Dish;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
import ua.training.service.UserService;
import ua.training.testData.CategoryTestDataGenerator;
import ua.training.testData.DishTestDataGenerator;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllDishesCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private DishService dishService;
    @Mock
    private CategoryService categoryService;

    private AllDishesCommand allDishesCommand;

    @Test
    public void shouldReturnAllDishesOnExecute() throws ServletException, IOException {
        List<Dish> dishes = DishTestDataGenerator.generateDishesList();
        List<Category> categories = CategoryTestDataGenerator.generateCategoryList();
        when(dishService.getAllDishes()).thenReturn(dishes);
        when(categoryService.getAllCategories()).thenReturn(categories);
        String expectedResult = Page.ALL_DISHES_VIEW;
        allDishesCommand = new AllDishesCommand(dishService, categoryService);

        String actualResult = allDishesCommand.execute(httpServletRequest, httpServletResponse);

        verify(httpServletRequest).setAttribute(Attribute.DISHES, dishes);
        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        assertEquals(expectedResult, actualResult);
    }
}
