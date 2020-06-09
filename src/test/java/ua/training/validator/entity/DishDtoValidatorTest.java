package ua.training.validator.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.dto.DishDto;
import ua.training.locale.Message;
import ua.training.testData.DishTestDataGenerator;
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
@PrepareForTest({FieldValidatorsChainGenerator.class, DishDtoValidator.class})
public class DishDtoValidatorTest {

    @Mock
    private AbstractFieldValidatorHandler abstractFieldValidatorHandler;
    @Captor
    private ArgumentCaptor<FieldValidatorKey> fieldValidatorKeyArgumentCaptor;

    private DishDtoValidator dishDtoValidator;

    @Test
    public void shouldReturnDishDtoValidatorInstanceOnGetInstance() throws Exception {
        dishDtoValidator = new DishDtoValidator();
        PowerMockito.whenNew(DishDtoValidator.class).withNoArguments().thenReturn(dishDtoValidator);

        DishDtoValidator.getInstance();

        PowerMockito.verifyNew(DishDtoValidator.class).withNoArguments();
    }

    @Test
    public void shouldReturnEmptyErrorListWhenEntityValidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        DishDto dishDto = DishTestDataGenerator.generateDishForCreation();
        List<String> expectedResult = new ArrayList<>();
        dishDtoValidator = new DishDtoValidator();

        List<String> actualResult = dishDtoValidator.validate(dishDto);

        verify(abstractFieldValidatorHandler, times(4)).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWhenEntityInvalidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        DishDto dishDto = DishTestDataGenerator.generateInvalidDish();
        List<String> expectedResult = new ArrayList<String>() {
            {
                add(Message.INVALID_NAME_INPUT);
            }
        };
        doAnswer(invocation -> {
            List<String> errorsList = invocation.getArgumentAt(2, List.class);
            errorsList.add(Message.INVALID_NAME_INPUT);
            return null;
        }).when(abstractFieldValidatorHandler).validateField(eq(FieldValidatorKey.NAME), eq(dishDto.getName()), anyListOf(String.class));
        dishDtoValidator = new DishDtoValidator();

        List<String> actualResult = dishDtoValidator.validate(dishDto);

        verify(abstractFieldValidatorHandler, times(4)).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }
}
