package ua.training.controller.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.testData.UserTestDataGenerator;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RedirectionManager.class, FieldValidatorsChainGenerator.class})
public class SearchUsersBySurnameCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Mock
    private AbstractFieldValidatorHandler abstractFieldValidatorHandler;
    @Mock
    private UserService userService;

    private SearchUsersBySurnameCommand searchUsersBySurnameCommand;

    @Test
    public void shouldFindUsersWhenValidInputOnExecute() throws ServletException, IOException {
        List<User> users = UserTestDataGenerator.generateUserForSearch();
        String surname = "testSurname";
        when(httpServletRequest.getParameter(Attribute.SURNAME)).thenReturn(surname);
        when(userService.searchUsersBySurname(surname)).thenReturn(users);
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        doNothing().when(abstractFieldValidatorHandler).validateField(eq(FieldValidatorKey.SURNAME), eq(surname), anyListOf(String.class));
        String expectedResult = Page.ALL_USERS_VIEW;
        searchUsersBySurnameCommand = new SearchUsersBySurnameCommand(userService);

        String actualResult = searchUsersBySurnameCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService).searchUsersBySurname(surname);
        verify(httpServletRequest).setAttribute(Attribute.USERS, users);
        verify(httpServletRequest).setAttribute(Attribute.ROLES, Role.values());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindUsersWhenInvalidInputOnExecute() throws ServletException, IOException {
        String surname = "";
        when(httpServletRequest.getParameter(Attribute.SURNAME)).thenReturn(surname);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        PowerMockito.mockStatic(FieldValidatorsChainGenerator.class);
        PowerMockito.when(FieldValidatorsChainGenerator.getFieldValidatorsChain()).thenReturn(abstractFieldValidatorHandler);
        List<String> errors = new ArrayList<>();
        doAnswer(invocation -> {
            List<String> errorsList = invocation.getArgumentAt(2, List.class);
            errorsList.add(Message.INVALID_SURNAME_INPUT);
            return null;
        }).when(abstractFieldValidatorHandler).validateField(FieldValidatorKey.SURNAME, surname, errors);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.INVALID_SURNAME_INPUT);
            }
        };
        searchUsersBySurnameCommand = new SearchUsersBySurnameCommand(userService);

        String actualResult = searchUsersBySurnameCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService, never()).searchUsersBySurname(surname);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_USERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotFindUsersWhenValidInputUserNotExistOnExecute() throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        String surname = "testSurname";
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(httpServletRequest.getParameter(Attribute.SURNAME)).thenReturn(surname);
        when(userService.searchUsersBySurname(surname)).thenReturn(users);
        String expectedResult = RedirectionManager.REDIRECTION;
        Map<String, String> urlParams = new HashMap<String, String>() {
            {
                put(Attribute.ERROR, Message.USER_IS_NOT_FOUND);
            }
        };
        searchUsersBySurnameCommand = new SearchUsersBySurnameCommand(userService);

        String actualResult = searchUsersBySurnameCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService).searchUsersBySurname(surname);
        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_USERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }
}
