package ua.training.controller.command.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.entity.Dish;
import ua.training.service.DishService;
import ua.training.testData.DishTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAddOrderCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private DishService dishService;

    private GetAddOrderCommand getAddOrderCommand;

    @Test
    public void shouldReturnAddOrderViewViewOnExecute() throws ServletException, IOException {
        List<Dish> dishes = DishTestDataGenerator.generateDishesList();
        when(dishService.getAllDishes()).thenReturn(dishes);
        String expectedResult = Page.ADD_UPDATE_ORDER_VIEW;
        getAddOrderCommand = new GetAddOrderCommand(dishService);

        String actualResult = getAddOrderCommand.execute(httpServletRequest, httpServletResponse);

        verify(dishService).getAllDishes();
        verify(httpServletRequest).setAttribute(Attribute.DISHES, dishes);
        assertEquals(expectedResult, actualResult);
    }
}
