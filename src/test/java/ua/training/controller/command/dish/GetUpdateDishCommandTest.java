package ua.training.controller.command.dish;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.user.GetUpdateUserCommand;
import ua.training.entity.Category;
import ua.training.entity.Dish;
import ua.training.entity.User;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
import ua.training.testData.CategoryTestDataGenerator;
import ua.training.testData.DishTestDataGenerator;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetUpdateDishCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private  DishService dishService;
    @Mock
    private  CategoryService categoryService;

    private GetUpdateDishCommand getUpdateDishCommand;

    @Test
    public void shouldReturnUpdateDishViewOnExecute() throws ServletException, IOException {
        when(httpServletRequest.getParameter(Attribute.ID_DISH)).thenReturn("1");
        Long dishId = 1L;
        Optional<Dish> dish = DishTestDataGenerator.generateOptionalDish();
        List<Category> categories = CategoryTestDataGenerator.generateCategoryList();
        when(categoryService.getAllCategories()).thenReturn(categories);
        when(dishService.getDishById(dishId)).thenReturn(dish);
        String expectedResult = Page.ADD_UPDATE_DISH_VIEW;
        getUpdateDishCommand = new GetUpdateDishCommand(dishService, categoryService);

        String actualResult = getUpdateDishCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).getDishById(dishId);
        verify(categoryService).getAllCategories();
        verify(httpServletRequest).setAttribute(Attribute.DISH, dish.get());
        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        assertEquals(expectedResult, actualResult);
    }
}
