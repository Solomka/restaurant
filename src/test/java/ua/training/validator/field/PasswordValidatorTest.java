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
@PrepareForTest(PasswordValidator.class)
public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        passwordValidator = new PasswordValidator(FieldValidatorKey.PASSWORD);
        PowerMockito.whenNew(PasswordValidator.class).withArguments(FieldValidatorKey.PASSWORD).thenReturn(passwordValidator);

        PasswordValidator.getInstance();

        PowerMockito.verifyNew(PasswordValidator.class).withArguments(FieldValidatorKey.PASSWORD);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        passwordValidator = new PasswordValidator(FieldValidatorKey.PASSWORD);
        List<String> actualResult = new ArrayList<>();

        passwordValidator.validateField("testPass333", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_PASS);
        passwordValidator = new PasswordValidator(FieldValidatorKey.PASSWORD);
        List<String> actualResult = new ArrayList<>();

        passwordValidator.validateField("111", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
