package ua.training.validator.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.dto.CredentialsDto;
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
@PrepareForTest({FieldValidatorsChainGenerator.class, CredentialsDtoValidator.class})
public class CredentialsDtoValidatorTest {
    @Mock
    private AbstractFieldValidatorHandler abstractFieldValidatorHandler;
    @Captor
    private ArgumentCaptor<FieldValidatorKey> fieldValidatorKeyArgumentCaptor;

    private CredentialsDtoValidator credentialsDtoValidator;

    @Test
    public void shouldReturnCategoryValidatorInstanceOnGetInstance() throws Exception {
        credentialsDtoValidator = new CredentialsDtoValidator();
        PowerMockito.whenNew(CredentialsDtoValidator.class).withNoArguments().thenReturn(credentialsDtoValidator);

        CredentialsDtoValidator.getInstance();

        PowerMockito.verifyNew(CredentialsDtoValidator.class).withNoArguments();
    }

    @Test
    public void shouldReturnEmptyErrorListWhenEntityValidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        CredentialsDto credentialsDto = UserTestDataGenerator.generateCredentialsDtoWithValidCreds();
        List<String> expectedResult = new ArrayList<>();
        credentialsDtoValidator = new CredentialsDtoValidator();

        List<String> actualResult = credentialsDtoValidator.validate(credentialsDto);

        verify(abstractFieldValidatorHandler, times(2)).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnErrorListWhenEntityInvalidOnValidate() {
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        CredentialsDto credentialsDto = UserTestDataGenerator.generateCredentialsDtoWithInvalidCreds();
        List<String> expectedResult = new ArrayList<String>() {
            {
                add(Message.INVALID_EMAIL);
            }
        };
        doAnswer(invocation -> {
            List<String> errorsList = invocation.getArgumentAt(2, List.class);
            errorsList.add(Message.INVALID_EMAIL);
            return null;
        }).when(abstractFieldValidatorHandler).validateField(eq(FieldValidatorKey.EMAIL), eq(credentialsDto.getEmail()), anyListOf(String.class));
        credentialsDtoValidator = new CredentialsDtoValidator();

        List<String> actualResult = credentialsDtoValidator.validate(credentialsDto);

        verify(abstractFieldValidatorHandler, times(2)).validateField(fieldValidatorKeyArgumentCaptor.capture(), anyString(), anyListOf(String.class));
        assertEquals(expectedResult, actualResult);
    }
}
