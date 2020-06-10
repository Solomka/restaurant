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
@PrepareForTest(EmailValidator.class)
public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        emailValidator = new EmailValidator(FieldValidatorKey.EMAIL);
        PowerMockito.whenNew(EmailValidator.class).withArguments(FieldValidatorKey.EMAIL).thenReturn(emailValidator);

        EmailValidator.getInstance();

        PowerMockito.verifyNew(EmailValidator.class).withArguments(FieldValidatorKey.EMAIL);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        emailValidator = new EmailValidator(FieldValidatorKey.EMAIL);
        List<String> actualResult = new ArrayList<>();

        emailValidator.validateField("test@gmail.com", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_EMAIL);
        emailValidator = new EmailValidator(FieldValidatorKey.EMAIL);
        List<String> actualResult = new ArrayList<>();

        emailValidator.validateField("bla.com", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
