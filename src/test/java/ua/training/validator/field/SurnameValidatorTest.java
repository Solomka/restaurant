package ua.training.validator.field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.locale.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SurnameValidator.class)
public class SurnameValidatorTest {

    private SurnameValidator surnameValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        surnameValidator = new SurnameValidator(FieldValidatorKey.SURNAME);
        PowerMockito.whenNew(SurnameValidator.class).withArguments(FieldValidatorKey.SURNAME).thenReturn(surnameValidator);

        SurnameValidator.getInstance();

        PowerMockito.verifyNew(SurnameValidator.class).withArguments(FieldValidatorKey.SURNAME);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        surnameValidator = new SurnameValidator(FieldValidatorKey.SURNAME);
        List<String> actualResult = new ArrayList<>();

        surnameValidator.validateField("testSurname", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_SURNAME);
        surnameValidator = new SurnameValidator(FieldValidatorKey.SURNAME);
        List<String> actualResult = new ArrayList<>();

        surnameValidator.validateField("testSurname1", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
