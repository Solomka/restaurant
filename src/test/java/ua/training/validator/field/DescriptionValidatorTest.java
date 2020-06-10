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
@PrepareForTest(DescriptionValidator.class)
public class DescriptionValidatorTest {

    private DescriptionValidator descriptionValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        descriptionValidator = new DescriptionValidator(FieldValidatorKey.DESCRIPTION);
        PowerMockito.whenNew(DescriptionValidator.class).withArguments(FieldValidatorKey.DESCRIPTION).thenReturn(descriptionValidator);

        DescriptionValidator.getInstance();

        PowerMockito.verifyNew(DescriptionValidator.class).withArguments(FieldValidatorKey.DESCRIPTION);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        descriptionValidator = new DescriptionValidator(FieldValidatorKey.DESCRIPTION);
        List<String> actualResult = new ArrayList<>();

        descriptionValidator.validateField("delicious", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_DESCRIPTION);
        descriptionValidator = new DescriptionValidator(FieldValidatorKey.DESCRIPTION);
        List<String> actualResult = new ArrayList<>();

        descriptionValidator.validateField("", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
