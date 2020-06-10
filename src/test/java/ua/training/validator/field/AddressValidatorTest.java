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
@PrepareForTest(AddressValidator.class)
public class AddressValidatorTest {

    private AddressValidator addressValidator;

    @Test
    public void shouldReturnNameValidatorInstanceOnGetInstance() throws Exception {
        addressValidator = new AddressValidator(FieldValidatorKey.ADDRESS);
        PowerMockito.whenNew(AddressValidator.class).withArguments(FieldValidatorKey.ADDRESS).thenReturn(addressValidator);

        AddressValidator.getInstance();

        PowerMockito.verifyNew(AddressValidator.class).withArguments(FieldValidatorKey.ADDRESS);
    }

    @Test
    public void shouldReturnEmptyErrorListWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.emptyList();
        addressValidator = new AddressValidator(FieldValidatorKey.ADDRESS);
        List<String> actualResult = new ArrayList<>();

        addressValidator.validateField("test address, 12", actualResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWithErrorWhenValidInputOnValidateField() {
        List<String> expectedResult = Collections.singletonList(Message.INVALID_ADDRESS);
        addressValidator = new AddressValidator(FieldValidatorKey.ADDRESS);
        List<String> actualResult = new ArrayList<>();

        addressValidator.validateField("", actualResult);

        assertEquals(expectedResult, actualResult);
    }
}
