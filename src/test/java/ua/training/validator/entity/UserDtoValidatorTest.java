package ua.training.validator.entity;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.dto.UserDto;
import ua.training.locale.Message;
import ua.training.testData.UserTestDataGenerator;
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
@PrepareForTest({FieldValidatorsChainGenerator.class, UserDtoValidator.class})
public class UserDtoValidatorTest {

    @Mock
    private AbstractFieldValidatorHandler abstractFieldValidatorHandler;
    @Captor
    private ArgumentCaptor<FieldValidatorKey> fieldValidatorKeyArgumentCaptor;

    private UserDtoValidator userDtoValidator;

    @Test
    public void shouldReturnUserDtoValidatorInstanceOnGetInstance() throws Exception {
        userDtoValidator = new UserDtoValidator();
        PowerMockito.whenNew(UserDtoValidator.class).withNoArguments().thenReturn(userDtoValidator);

        UserDtoValidator.getInstance();

        PowerMockito.verifyNew(UserDtoValidator.class).withNoArguments();
    }

    @Test
    public void shouldReturnEmptyErrorListWhenEntityValidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        UserDto userDto = UserTestDataGenerator.generateUserForCreation();
        List<String> expectedResult = new ArrayList<>();
        userDtoValidator = new UserDtoValidator();

        List<String> actualResult = userDtoValidator.validate(userDto);

        verify(abstractFieldValidatorHandler, times(5)).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }

    /*@Test
    public void shouldReturnErrorListWhenEntityInvalidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        UserDto userDto = UserTestDataGenerator.generateInvalidUserDto();
        List<String> expectedResult = new ArrayList<String>() {
            {
                add(Message.INVALID_NAME);
            }
        };
        doAnswer(invocation -> {
            List<String> errorsList = invocation.getArgumentAt(2, List.class);
            errorsList.add(Message.INVALID_NAME);
            return null;
        }).when(abstractFieldValidatorHandler).validateField(eq(FieldValidatorKey.NAME), eq(userDto.getName()), anyListOf(String.class));
        userDtoValidator = new UserDtoValidator();

        List<String> actualResult = userDtoValidator.validate(userDto);

        verify(abstractFieldValidatorHandler, times(5)).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }*/
}
