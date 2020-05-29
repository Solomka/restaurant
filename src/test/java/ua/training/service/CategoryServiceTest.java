package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.dao.CategoryDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.Category;
import ua.training.testData.CategoryTestDataGenerator;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static ua.training.service.CategoryService.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class CategoryServiceTest {

    private static Logger LOGGER;

    @Mock
    private DaoFactory daoFactory;
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryDao categoryDao;

    @BeforeClass
    public static void setUp() {
        LOGGER = PowerMockito.mock(Logger.class);
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getLogger(CategoryService.class)).thenReturn(LOGGER);
    }

    @Before
    public void setUpBeforeMethod() {
        categoryService = new CategoryService(daoFactory);
        when(daoFactory.createCategoryDao()).thenReturn(categoryDao);
    }

    @Test
    public void shouldGetAllCategoriesOnGetAllCategories() {
        List<Category> expectedResult = CategoryTestDataGenerator.generateCategoryList();
        when(categoryDao.getAll()).thenReturn(expectedResult);

        List<Category> actualResult = categoryService.getAllCategories();

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(GET_ALL_CATEGORIES);
        verify(daoFactory).createCategoryDao();
        verify(categoryDao).getAll();
    }

    @Test
    public void shouldReturnCategoryOnGetCategoryById() {
        Optional<Category> expectedResult = CategoryTestDataGenerator.generateOptionalCategory();
        Long categoryId = 1L;
        when(categoryDao.getById(categoryId)).thenReturn(expectedResult);

        Optional<Category> actualResult = categoryService.getCategoryById(categoryId);

        assertEquals(expectedResult.get(), actualResult.get());
        verify(LOGGER, times(1)).info(String.format(GET_CATEGORY_BY_ID, categoryId));
        verify(daoFactory).createCategoryDao();
        verify(categoryDao).getById(categoryId);
    }

    @Test
    public void shouldCreateCategoryOnCreateCategory() {
        Category category = CategoryTestDataGenerator.generateCategoryForCreation();

        categoryService.createCategory(category);

        verify(LOGGER, times(1)).info(String.format(CREATE_CATEGORY, category.getName()));
        verify(daoFactory).createCategoryDao();
        verify(categoryDao).create(category);
    }

    @Test
    public void shouldUpdateUserOnUpdateUser() {
        Category category = CategoryTestDataGenerator.generateCategoryForUpdate();

        categoryService.updateCategory(category);

        verify(LOGGER, times(1)).info(String.format(UPDATE_CATEGORY, category.getId()));
        verify(daoFactory).createCategoryDao();
        verify(categoryDao).update(category);
    }

    @Test
    public void shouldDeleteUserOnDeleteUser() {
        Long categoryId = 1L;

        categoryService.deleteCategory(categoryId);

        verify(LOGGER, times(1)).info(String.format(DELETE_CATEGORY, categoryId));
        verify(daoFactory).createCategoryDao();
        verify(categoryDao).delete(categoryId);
    }

    @Test
    public void shouldSearchCategoryOnSearchCategoriesByName() {
        List<Category> expectedResult = CategoryTestDataGenerator.generateCategoryForSearch();
        String name = "meat";
        when(categoryDao.searchCategoriesByName(name)).thenReturn(expectedResult);

        List<Category> actualResult = categoryService.searchCategoriesByName(name);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_CATEGORIES_BY_NAME, name));
        verify(daoFactory).createCategoryDao();
        verify(categoryDao).searchCategoriesByName(name);
    }
}
