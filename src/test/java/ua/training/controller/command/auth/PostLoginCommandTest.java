package ua.training.controller.command.auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.dto.CredentialsDto;
import ua.training.entity.User;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SessionManager.class, RedirectionManager.class})
public class PostLoginCommandTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private PostLoginCommand postLoginCommand;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private HttpSession httpSession;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;

    @Before
    public void setUpBeforeMethod() {
        postLoginCommand = new PostLoginCommand(userService);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
    }

    private void initMethodsStubbingForNotLoggedInUserValidInput() {
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(Attribute.USER)).thenReturn(null);
        when(httpServletRequest.getContextPath()).thenReturn("\\restaurant");
        when(httpServletRequest.getServletPath()).thenReturn("\\controller");
        when(httpServletRequest.getParameter(Attribute.EMAIL)).thenReturn("pytlyk@gmail.com");
        when(httpServletRequest.getParameter(Attribute.PASSWORD)).thenReturn("pytlyk777");
    }

    @Test
    public void shouldLogInWhenValidInputUserIsPresentOnExecute() throws ServletException, IOException {
        Optional<User> user = UserTestDataGenerator.generateOptionalUser();
        CredentialsDto credentialsDto = UserTestDataGenerator.generateCredentialsDtoWithValidCreds();
        String expectedResultedResource = RedirectionManager.REDIRECTION;

        initMethodsStubbingForNotLoggedInUserValidInput();
        PowerMockito.mockStatic(SessionManager.class);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(SessionManager.getInstance()).thenReturn(sessionManager);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(userService.getUserByCredentials(credentialsDto)).thenReturn(user);

        String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService).getUserByCredentials(credentialsDto);
        verify(sessionManager).addUserToSession(httpSession, user.get());
        verify(redirectionManager).redirect(httpWrapperArgumentCaptor.capture(), eq(ServletPath.HOME));
        assertEquals(expectedResultedResource, actualResultedResource);
    }

    @Test
    public void shouldNotLogInWhenUserIsNotPresentWithCredsOnExecute() throws ServletException, IOException {
        Optional<User> user = Optional.empty();
        CredentialsDto credentialsDto = UserTestDataGenerator.generateCredentialsDtoWithValidCreds();
        String expectedResultedResource = Page.LOGIN_VIEW;
        List<String> errors = Collections.singletonList(Message.INVALID_CREDENTIALS);

        initMethodsStubbingForNotLoggedInUserValidInput();
        when(userService.getUserByCredentials(credentialsDto)).thenReturn(user);

        String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService).getUserByCredentials(credentialsDto);
        verify(httpServletRequest).setAttribute(Attribute.LOGIN_USER, credentialsDto);
        verify(httpServletRequest).setAttribute(Attribute.ERRORS, errors);
        assertEquals(expectedResultedResource, actualResultedResource);
    }

    @Test
    public void shouldNotLogInWhenInvalidInputOnExecute() throws ServletException, IOException {
        when(httpSession.getAttribute(Attribute.USER)).thenReturn(null);
        when(httpServletRequest.getParameter(Attribute.EMAIL)).thenReturn("");
        when(httpServletRequest.getParameter(Attribute.PASSWORD)).thenReturn("");
        String expectedResultedResource = Page.LOGIN_VIEW;

        String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService, never()).getUserByCredentials(anyObject());
        verify(httpServletRequest).setAttribute(eq(Attribute.LOGIN_USER), anyObject());
        verify(httpServletRequest).setAttribute(eq(Attribute.ERRORS), anyListOf(String.class));
        assertEquals(expectedResultedResource, actualResultedResource);
    }

    @Test
    public void shouldNotLogInWhenUserLoggedInOnExecute() throws ServletException, IOException {
        PowerMockito.mockStatic(SessionManager.class);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(SessionManager.getInstance()).thenReturn(sessionManager);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(sessionManager.isUserLoggedIn(httpSession)).thenReturn(true);
        User user = UserTestDataGenerator.generateUser();
        when(httpSession.getAttribute(Attribute.USER)).thenReturn(user);
        when(httpServletRequest.getContextPath()).thenReturn("\\restaurant");
        when(httpServletRequest.getServletPath()).thenReturn("\\controller");
        CredentialsDto credentialsDto = UserTestDataGenerator.generateCredentialsDtoWithValidCreds();
        String expectedResultedResource = RedirectionManager.REDIRECTION;

        String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

        verify(userService, never()).getUserByCredentials(credentialsDto);
        verify(redirectionManager).redirect(httpWrapperArgumentCaptor.capture(), eq(ServletPath.HOME));
        assertEquals(expectedResultedResource, actualResultedResource);
    }
}
