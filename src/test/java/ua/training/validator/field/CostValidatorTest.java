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
@PrepareForTest(CostValidator.class)
public class CostValidatorTest {

    private CostValidator costValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        costValidator = new CostValidator(FieldValidatorKey.COST);
        PowerMockito.whenNew(CostValidator.class).withArguments(FieldValidatorKey.COST).thenReturn(costValidator);

        CostValidator.getInstance();

        PowerMockito.verifyNew(CostValidator.class).withArguments(FieldValidatorKey.COST);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        costValidator = new CostValidator(FieldValidatorKey.COST);
        List<String> actualResult = new ArrayList<>();

        costValidator.validateField("180.50", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_COST);
        costValidator = new CostValidator(FieldValidatorKey.COST);
        List<String> actualResult = new ArrayList<>();

        costValidator.validateField("180..5", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
