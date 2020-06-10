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
@PrepareForTest(PhoneValidator.class)
public class PhoneValidatorTest {

    private PhoneValidator phoneValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        phoneValidator = new PhoneValidator(FieldValidatorKey.PHONE);
        PowerMockito.whenNew(PhoneValidator.class).withArguments(FieldValidatorKey.PHONE).thenReturn(phoneValidator);

        PhoneValidator.getInstance();

        PowerMockito.verifyNew(PhoneValidator.class).withArguments(FieldValidatorKey.PHONE);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        phoneValidator = new PhoneValidator(FieldValidatorKey.PHONE);
        List<String> actualResult = new ArrayList<>();

        phoneValidator.validateField("0984560923", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_PHONE);
        phoneValidator = new PhoneValidator(FieldValidatorKey.PHONE);
        List<String> actualResult = new ArrayList<>();

        phoneValidator.validateField("`123`", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
