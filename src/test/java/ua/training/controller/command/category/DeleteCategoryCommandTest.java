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
import ua.training.constants.ServletPath;
import ua.training.controller.command.user.DeleteUserCommand;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Category;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectionManager.class)
public class DeleteCategoryCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Mock
    private CategoryService categoryService;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;

    private DeleteCategoryCommand deleteCategoryCommand;

    @Test
    public void shouldDeleteCategoryOnExecute() throws Exception {
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        Long categoryId = 1L;
        when(httpServletRequest.getParameter(Attribute.ID_CATEGORY)).thenReturn("1");
        doNothing().when(categoryService).deleteCategory(categoryId);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(Attribute.SUCCESS, Message.SUCCESS_CATEGORY_DELETE);
        String expectedResult = RedirectionManager.REDIRECTION;
        deleteCategoryCommand = new DeleteCategoryCommand(categoryService);

        String actualResult = deleteCategoryCommand.execute(httpServletRequest, httpServletResponse);

        verify(categoryService).deleteCategory(categoryId);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_CATEGORIES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
