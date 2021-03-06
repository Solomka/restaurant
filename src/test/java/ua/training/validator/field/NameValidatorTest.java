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
@PrepareForTest(NameValidator.class)
public class NameValidatorTest {

    private NameValidator nameValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        nameValidator = new NameValidator(FieldValidatorKey.NAME);
        PowerMockito.whenNew(NameValidator.class).withArguments(FieldValidatorKey.NAME).thenReturn(nameValidator);

        NameValidator.getInstance();

        PowerMockito.verifyNew(NameValidator.class).withArguments(FieldValidatorKey.NAME);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        nameValidator = new NameValidator(FieldValidatorKey.NAME);
        List<String> actualResult = new ArrayList<>();

        nameValidator.validateField("testName", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_NAME);
        nameValidator = new NameValidator(FieldValidatorKey.NAME);
        List<String> actualResult = new ArrayList<>();

        nameValidator.validateField("testSurname1", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
