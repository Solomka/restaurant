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
@PrepareForTest(WeightValidator.class)
public class WeightValidatorTest {

    private WeightValidator weightValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        weightValidator = new WeightValidator(FieldValidatorKey.WEIGHT);
        PowerMockito.whenNew(WeightValidator.class).withArguments(FieldValidatorKey.WEIGHT).thenReturn(weightValidator);

        WeightValidator.getInstance();

        PowerMockito.verifyNew(WeightValidator.class).withArguments(FieldValidatorKey.WEIGHT);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        weightValidator = new WeightValidator(FieldValidatorKey.WEIGHT);
        List<String> actualResult = new ArrayList<>();

        weightValidator.validateField("150", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_WEIGHT);
        weightValidator = new WeightValidator(FieldValidatorKey.WEIGHT);
        List<String> actualResult = new ArrayList<>();

        weightValidator.validateField("test", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
