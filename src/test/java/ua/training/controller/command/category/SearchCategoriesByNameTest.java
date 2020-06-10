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
public class SearchCategoriesByNameTest {


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
    private CategoryService categoryService;

    private SearchCategoriesByName searchCategoriesByName;

    @Test
    public void shouldFindCategoriesWhenValidInputOnExecute() throws ServletException, IOException {
        List<Category> categories = CategoryTestDataGenerator.generateCategoryForSearch();
        String name = "meat";
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn(name);
        when(categoryService.searchCategoriesByName(name)).thenReturn(categories);
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(eq(FieldValidatorKey.NAME), eq(name), anyListOf(String.class));
        String expectedResult = Page.ALL_CATEGORIES_VIEW;
        searchCategoriesByName = new SearchCategoriesByName(categoryService);

        String actualResult = searchCategoriesByName.execute(httpServletRequest, httpServletResponse);

        verify(categoryService).searchCategoriesByName(name);
        verify(httpServletRequest).setAttribute(Attribute.CATEGORIES, categories);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindCategoriesWhenInvalidInputOnExecute() throws ServletException, IOException {
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
        searchCategoriesByName = new SearchCategoriesByName(categoryService);

        String actualResult = searchCategoriesByName.execute(httpServletRequest, httpServletResponse);

        verify(categoryService, never()).searchCategoriesByName(name);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_CATEGORIES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindCategoriesWhenValidInputCategoryNotExistOnExecute() throws ServletException, IOException {
        List<Category> categories = new ArrayList<>();
        String name = "meat";
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn(name);
        when(categoryService.searchCategoriesByName(name)).thenReturn(categories);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.CATEGORY_IS_NOT_FOUND);
            }
        };
        searchCategoriesByName = new SearchCategoriesByName(categoryService);

        String actualResult = searchCategoriesByName.execute(httpServletRequest, httpServletResponse);

        verify(categoryService).searchCategoriesByName(name);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_CATEGORIES), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
