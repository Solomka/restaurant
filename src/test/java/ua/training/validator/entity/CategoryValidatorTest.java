package ua.training.validator.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.entity.Category;
import ua.training.locale.Message;
import ua.training.testData.CategoryTestDataGenerator;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FieldValidatorsChainGenerator.class, CategoryValidator.class})
public class CategoryValidatorTest {

    @Mock
    private AbstractFieldValidatorHandler abstractFieldValidatorHandler;
    @Captor
    private ArgumentCaptor<FieldValidatorKey> fieldValidatorKeyArgumentCaptor;

    private CategoryValidator categoryValidator;

    @Test
    public void shouldReturnCategoryValidatorInstanceOnGetInstance() throws Exception {
        categoryValidator = new CategoryValidator();
        PowerMockito.whenNew(CategoryValidator.class).withNoArguments().thenReturn(categoryValidator);

        CategoryValidator.getInstance();

        PowerMockito.verifyNew(CategoryValidator.class).withNoArguments();
    }

    @Test
    public void shouldReturnEmptyErrorListWhenEntityValidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        Category category = CategoryTestDataGenerator.generateCategoryForCreation();
        List<String> expectedResult = new ArrayList<>();
        categoryValidator = new CategoryValidator();

        List<String> actualResult = categoryValidator.validate(category);

        verify(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWhenEntityInvalidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        Category category = CategoryTestDataGenerator.generateInvalidCategory();
        List<String> expectedResult = new ArrayList<String>() {
            {
                add(Message.INVALID_NAME_INPUT);
            }
        };
        doAnswer(invocation -> {
            List<String> errorsList = invocation.getArgumentAt(2, List.class);
            errorsList.add(Message.INVALID_NAME_INPUT);
            return null;
        }).when(abstractFieldValidatorHandler).validateField(eq(FieldValidatorKey.NAME), eq(category.getName()), anyListOf(String.class));
        categoryValidator = new CategoryValidator();

        List<String> actualResult = categoryValidator.validate(category);

        verify(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }
}
